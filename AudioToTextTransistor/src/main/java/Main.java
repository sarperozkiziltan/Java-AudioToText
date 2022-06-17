import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.net.http.*;

public class Main {
    private static  final String video_url="https://github.com/sarperozkiziltan/audio-files/blob/main/how-to-run-java-programs-with-command-prompt-cmd-and-notepad-in-windows.mp3?raw=true";
    private static final String APItoken="ddd26fc4c2f940b6bba4367a384409d4";
    private static final String requestURL ="https://api.assemblyai.com/v2/transcript";
    private static final String fileLocation ="transcriptText.txt";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Transcript transcript=new Transcript();
        transcript.setAudio_url(video_url);
        Gson gson =new Gson();
        String jsonRequest = gson.toJson(transcript);

        HttpRequest postRequest= HttpRequest.newBuilder()
                .uri(new URI(requestURL))
                .header("Authorization",APItoken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        transcript = gson.fromJson(postResponse.body(),Transcript.class);

        HttpRequest getRequest= HttpRequest.newBuilder()
                .uri(new URI(requestURL +"/"+transcript.getId()))
                .header("Authorization",APItoken)
                .build();

        while (true){
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        transcript = gson.fromJson(getResponse.body(),Transcript.class);
        System.out.print(transcript.getStatus().equals("queued") ? "your file is in the queue\n" : "");
        System.out.print(transcript.getStatus().equals("processing") ? "your file is processing at the moment wait until it finishes\n" : "");

        if(transcript.getStatus().equals("completed")){
            System.out.println("Transaction Completed Without Any Failure:) Now Check TranscriptText.txt Under The Project Files! And also you can see your text in our gui which we made for you:)");
            break;
        }else if(transcript.getStatus().equals("error")){
            System.out.println("You have an error there is something wrong :(");
            break;
        }
            Thread.sleep(5000);
        }
        writeToFile(transcript.getText());
        new InfoTextPage();
    }
    private static void writeToFile(String text) {
        String[] words = text.split(" ");
        //append'in anlami eğer true yapılırsa
        // dosyadaki halihazirda bulunan dökümanları silmeyip yanina ekleyecek, eger false olursa
        //dosyadaki herseyi silip üstüne yazicak
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation,false))){
            for(int i=0;i<words.length;i++){
                writer.write(words[i]+" ");
                if(i%20==0 && i!=0){
                    writer.newLine();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    static String getFileLocation() {
        return fileLocation;
    }
    }
