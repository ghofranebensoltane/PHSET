package com.pidev.phset.configures;

import com.google.gson.Gson;
import com.pidev.phset.entities.*;
import com.pidev.phset.services.IEvaluationServices;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ClaimWebSocketHandler extends TextWebSocketHandler {

    private IEvaluationServices evaluationServices;

    public ClaimWebSocketHandler(IEvaluationServices evaluationServices) {
        this.evaluationServices = evaluationServices;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Envoyer toutes les réclamations existantes au client lorsqu'une nouvelle connexion WebSocket est établie
        List<Claim> claims = evaluationServices.retrieveAllClaims();
        for (Claim claim : claims) {
            sendClaim(claim, session);
        }
    }


    public void sendClaim(Claim claim, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(new Gson().toJson(claim)));
    }

    public void sendClaimUpdate(Claim claim, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(new Gson().toJson(evaluationServices.updateClaim(claim))));
    }

    public void sendClaimDelete(int claimId, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(new Gson().toJson(evaluationServices.deleteClaim(claimId))));
    }

}
