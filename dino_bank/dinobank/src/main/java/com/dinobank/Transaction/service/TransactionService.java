package com.dinobank.Transaction.service;

import com.dinobank.Transaction.dto.TransactionRequestDto;
import com.dinobank.Transaction.dto.TransactionResponseDto;
import com.dinobank.model.Account;
import com.dinobank.model.Transaction;
import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional 
    public TransactionResponseDto transferMoney(TransactionRequestDto request) {

        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber());
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber());

        if (fromAccount == null || toAccount == null) {
            throw new RuntimeException("Gönderen veya Alıcı hesap bulunamadı!");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Yetersiz Bakiye!");
        }

        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            throw new RuntimeException("Farklı para birimleri arasında transfer yapılamaz!");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(fromAccount.getCurrency());
        transaction.setTransactionType("TRANSFER");
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDto(
                savedTransaction.getId(),
                fromAccount.getAccountNumber(),
                toAccount.getAccountNumber(),
                savedTransaction.getAmount(),
                savedTransaction.getTransactionType(),
                savedTransaction.getStatus(),
                savedTransaction.getTransactionDate());
    }

    @Transactional
    public TransactionResponseDto deposit(TransactionRequestDto request) {
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber());

        if (toAccount == null) {
            throw new RuntimeException("Hesap bulunamadı!");
        }

        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(null);
        transaction.setToAccount(toAccount);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(toAccount.getCurrency());
        transaction.setTransactionType("DEPOSIT");
        transaction.setDescription("ATM Para Yatırma");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");

        Transaction savedTx = transactionRepository.save(transaction);

        return new TransactionResponseDto(
                savedTx.getId(), "ATM", toAccount.getAccountNumber(),
                savedTx.getAmount(), savedTx.getTransactionType(),
                savedTx.getStatus(), savedTx.getTransactionDate());
    }

    public List<TransactionResponseDto> getTransactionHistory(Long accountId) {
        return transactionRepository.findByFromAccount_IdOrToAccount_IdOrderByTransactionDateDesc(accountId, accountId)
                .stream()
                .map(tx -> new TransactionResponseDto(
                        tx.getId(),
                        tx.getFromAccount() != null ? tx.getFromAccount().getAccountNumber() : "ATM", // Null kontrolü
                        tx.getToAccount() != null ? tx.getToAccount().getAccountNumber() : "-",
                        tx.getAmount(),
                        tx.getTransactionType(),
                        tx.getStatus(),
                        tx.getTransactionDate()))
                .collect(Collectors.toList());
    }
}