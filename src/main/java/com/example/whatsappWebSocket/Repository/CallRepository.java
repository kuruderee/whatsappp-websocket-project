package com.example.whatsappWebSocket.Repository;

import com.example.whatsappWebSocket.template.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CallRepository extends JpaRepository<Call, String> {
    Optional<Call> findById(String id);

    List<Call> findAll();

    List<Call> findByCallerUsernameOrReceiverUsername(String callerUsername, String receiverUsername);



    boolean existsByCallerUsernameAndCallStatus(String callerUsername, String callStatus);


    Optional<Call> findByReceiverUsername(String receiverUsername);
}

