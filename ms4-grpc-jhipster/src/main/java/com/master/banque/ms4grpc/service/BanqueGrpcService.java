package com.master.banque.ms4grpc.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.master.banque.grpc.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@GrpcService
@Service
public class BanqueGrpcService extends BanqueServiceGrpc.BanqueServiceImplBase {

    private final Logger log = LoggerFactory.getLogger(BanqueGrpcService.class);

    @Override
    public void creerClient(ClientRequest request, StreamObserver<ClientResponse> responseObserver) {
        log.info("Création client pour le numéro: {}", request.getNumeroTel());
        
        try {
            ClientResponse response = ClientResponse.newBuilder()
                .setStatus("SUCCESS")
                .setMessage("Client créé avec succès")
                .setNumeroTel(request.getNumeroTel())
                .setSoldeActuel(request.getSoldeInitial())
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Erreur lors de la création du client", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void consulterSolde(SoldeRequest request, StreamObserver<SoldeResponse> responseObserver) {
        log.info("Consultation solde pour le numéro: {}", request.getNumeroTel());
        
        try {
            // Simulation d'un solde
            long solde = 150000L; // 1500.00 MAD
            
            SoldeResponse response = SoldeResponse.newBuilder()
                .setNumeroTel(request.getNumeroTel())
                .setSolde(solde)
                .setDateConsultation(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Erreur lors de la consultation du solde", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void effectuerTransfert(TransfertRequest request, StreamObserver<TransfertResponse> responseObserver) {
        log.info("Transfert de {} de {} vers {}", 
            request.getMontant(), request.getEmetteur(), request.getDestinataire());
        
        try {
            // Simulation d'un transfert réussi
            TransfertResponse response = TransfertResponse.newBuilder()
                .setStatus("SUCCESS")
                .setMessage("Transfert effectué avec succès")
                .setReference("TRF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .setNouveauSoldeEmetteur(85000L) // Simulation
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Erreur lors du transfert", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void streamTransactions(StreamRequest request, StreamObserver<Transaction> responseObserver) {
        log.info("Streaming transactions pour le numéro: {}, max: {}", 
            request.getNumeroTel(), request.getMaxTransactions());
        
        try {
            for (int i = 0; i < request.getMaxTransactions(); i++) {
                Transaction transaction = Transaction.newBuilder()
                    .setId("TXN-" + UUID.randomUUID().toString().substring(0, 8))
                    .setType(i % 2 == 0 ? "CREDIT" : "DEBIT")
                    .setMontant((i + 1) * 1000L)
                    .setDate(LocalDateTime.now().minusDays(i).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .setDescription(i % 2 == 0 ? "Dépôt" : "Retrait")
                    .build();

                responseObserver.onNext(transaction);
                
                // Simulation d'un délai entre les transactions
                Thread.sleep(500);
            }
            
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Erreur lors du streaming des transactions", e);
            responseObserver.onError(e);
        }
    }
}