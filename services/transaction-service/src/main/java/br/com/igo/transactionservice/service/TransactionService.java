package br.com.igo.transactionservice.service;

import br.com.igo.transactionservice.dto.TransactionRequestDTO;
import br.com.igo.transactionservice.dto.TransactionResponseDTO;
import br.com.igo.transactionservice.entity.Transaction;
import br.com.igo.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public TransactionResponseDTO create(TransactionRequestDTO dto){

        Transaction transaction = new Transaction();

        transaction.setAccountId(dto.accountId());
        transaction.setType(dto.type());
        transaction.setAmount(dto.amount());
        transaction.setStatus("PENDING");

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponseDTO(savedTransaction);
    }

    private TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );
    }
}
