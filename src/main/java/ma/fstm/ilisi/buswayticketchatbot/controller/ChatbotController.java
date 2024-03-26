package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.dto.ChatMessage;
import ma.fstm.ilisi.buswayticketchatbot.service.StationService;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {
    private final Bot bot;
    private final Chat chatSession;
    private final StationService stationService;

    @Autowired
    public ChatbotController(StationService stationService) {
        this.bot = new Bot("busway", "src/main/resources");
        this.chatSession = new Chat(bot);
        this.stationService = stationService;
    }

    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody ChatMessage chatMessage) {
        String botResponse = chatSession.multisentenceRespond(chatMessage.getMessage().toUpperCase());
        Map<String, String> response = new HashMap<>();

        switch (botResponse) {
            case "Merci de me fournir votre emplacement..." -> {
                response.put("action", "NEED_LOCATION");
            }
        }
        response.put("reply", botResponse);
        return ResponseEntity.ok(response);
    }

}
