package com.example.whatsappWebSocket.Controller;


import com.example.whatsappWebSocket.Repository.CallRepository;
import com.example.whatsappWebSocket.Service.CallService;
import com.example.whatsappWebSocket.template.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CallController {

    @Autowired
    private CallService voiceCallService;

    private final CallRepository callDataRepository;



    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @GetMapping("/call")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("deneme");
    }

    public CallController(SimpMessagingTemplate messagingTemplate, CallRepository callDataRepository) {
        this.messagingTemplate = messagingTemplate;
        this.callDataRepository = callDataRepository;
    }
    @PostMapping("/test/saveCall")
    public String saveCall(@RequestBody Call call) {
        voiceCallService.saveCall(call);
        return "Call saved!";
    }

    @MessageMapping("/call/start")
    public void startCall(@Payload Call call) {
        System.out.println("startCall metodu çağrıldı. Gelen çağrı: {}"+ call);
        if (call.getCallerUsername() == null) {
            System.out.println("Eksik sinyal bilgisi: {}"+ call);
            return;
        }

        String callerUsername = call.getCallerUsername();
        String receiverUsername = call.getReceiverUsername();


        Call savedCall = voiceCallService.startCall(callerUsername, receiverUsername);
        System.out.println("Service'den dönen çağrı bilgisi: {}"+ savedCall);

        if (savedCall != null) {
            messagingTemplate.convertAndSendToUser(
                    receiverUsername,
                    "/queue/call",
                    savedCall
            );
            messagingTemplate.convertAndSendToUser(
                    savedCall.getCallerUsername(),
                    "/queue/call",
                    savedCall
            );
            System.out.println("Çağrı bilgisi kullanıcılara gönderildi: {}"+ savedCall);
        } else {
            System.out.println("Çağrı başlatılamadı! Service null döndü.");
        }
        System.out.println("Call accepted by: " + call.getReceiverUsername());
    }
    @MessageMapping("/call/signal")
    public void handleSignal(@Payload Call signalMessage) {
        System.out.println("handleSignal metodu çağrıldı. Gelen sinyal: {}"+ signalMessage);
        if (signalMessage.getCallerUsername() == null || signalMessage.getReceiverUsername() == null) {
            System.out.println("Eksik sinyal bilgisi: {}"+ signalMessage);
            return;
        }

        voiceCallService.saveCall(signalMessage);

        messagingTemplate.convertAndSendToUser(
                signalMessage.getReceiverUsername(),
                "/queue/call",
                signalMessage
        );
    }

    @MessageMapping("/call/reject")
    public void rejectCall(@Payload Call call) {
        call.setCallStatus("REJECTED");
        voiceCallService.rejectCall(call.getCallerUsername());

        messagingTemplate.convertAndSendToUser(
                call.getCallerUsername(),
                "/queue/call",
                call
        );
    }

    @MessageMapping("/call/end")
    public void endCall(@Payload Call call) {
        if (call.getCallerUsername() == null || call.getReceiverUsername() == null) {
            System.out.println("Eksik çağrı bilgisi! Çağrı sonlandırılamaz.");
            return;
        }
        System.out.println("Call ended by: " + call.getReceiverUsername() + ", Duration: " + call.getDuration() + " seconds");
        call.setCallStatus("ENDED");
        callDataRepository.save(call);
        voiceCallService.endCall(call.getCallerUsername());

        messagingTemplate.convertAndSendToUser(
                call.getCallerUsername(),
                "/queue/call",
                call
        );
        messagingTemplate.convertAndSendToUser(
                call.getReceiverUsername(),
                "/queue/call",
                call
        );
    }

    @MessageMapping("/call/accept")
    public void acceptCall(@Payload Call call) {
        if (call.getCallerUsername() == null || call.getReceiverUsername() == null) {
            System.out.println("Arama kabul edilemedi! Eksik bilgiler: {}"+ call);
            return;
        }
        // voiceCallService.acceptCall(call.getCallerUsername());
        call.setCallStatus("ACCEPTED");

        messagingTemplate.convertAndSendToUser(
                call.getCallerUsername(),
                "/queue/call",
                call
        );
    }
}
