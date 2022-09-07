package com.codeninja.controllers;

import com.codeninja.models.Account;
import com.codeninja.models.AccountStatus;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Path("/accounts")
public class accountsResource {

    Set<Account> accounts = new LinkedHashSet<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Account> allAccounts() {
        return new LinkedHashSet<>(accounts);
    }

    @PostConstruct
    public void initSetUp() {
        accounts.add(new Account(1L, 1L, "John Smith", BigDecimal.valueOf(1000), AccountStatus.OPEN));
        accounts.add(new Account(2L, 2L, "Jane Doe", BigDecimal.valueOf(2000), AccountStatus.OPEN));
        accounts.add(new Account(3L, 3L, "Joe Blogs", BigDecimal.valueOf(3000), AccountStatus.OPEN));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{accountNumber}")
    public Account getAccount(@PathParam("accountNumber") Long accountNumber) {
        Optional<Account> response = accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst();

        return response.orElseThrow(()
                -> new WebApplicationException("Account with id of " + accountNumber + " does not exist.", 404));

    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{accountNumber}/withdraw")
    public Account withdrawFunds(@PathParam("accountNumber") Long accountNumber, BigDecimal amount) {

        log.info("Attempting to withdraw {} from account {}", amount, accountNumber);
        Optional<Account> response = accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst();
        Account account = response.orElseThrow(()
                -> new WebApplicationException("Account with id of " + accountNumber + " does not exist.", 404));
        account.withdrawFunds(amount);
        Optional<Account> response2 = account.getBalance().compareTo(BigDecimal.ZERO)
                < amount.compareTo(BigDecimal.TEN) ? Optional.of(account) : Optional.empty();
        log.info("Account balance is {}", account.getBalance());
        return response2.orElseThrow(()
                -> new WebApplicationException("Insufficient funds", 400));

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/creatingNewAccount")
    public Account createNewAccount(Account account) {
        accounts.add(account);
        log.info("creating account with number " + account);
        if (account.getAccountNumber() == null) {
            account.setAccountNumber((long) (accounts.size() + 1));
        } else {
            if (accounts.stream().anyMatch(a -> a.getAccountNumber().equals(account.getAccountNumber()))) {
                throw new WebApplicationException("Account with id of " + account.getAccountNumber() + " already exists.", 400);
            }
        }
        log.info("Account created: " + account);
        return account;
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{accountNumber}/deposit")
    public Account depositFunds(@PathParam("accountNumber") Long accountNumber, BigDecimal amount) {
        log.info("Deposit funds: " + amount);
        Optional<Account> response = accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst();
        Account account = response.orElseThrow(()
                -> new WebApplicationException("Account with id of " + accountNumber + " does not exist.", 404));
        account.addFunds(amount);
        log.info("Account balance: " + account.getBalance());
        return account;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{accountNumber}/delete")
    public Account deleteAccount(@PathParam("accountNumber") Long accountNumber) {
        log.info("Delete account: " + accountNumber);
        Optional<Account> response = accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst();
        Account account = response.orElseThrow(()
                -> new WebApplicationException("Account with id of " + accountNumber + " does not exist.", 404));
        accounts.remove(account);
        log.info("Account deleted: " + account);
        return account;
    }

    @Provider
    public static class AccountNotFoundExceptionMapper implements ExceptionMapper<WebApplicationException> {

        @Override
        public Response toResponse(WebApplicationException exception) {
            return Response.status(exception.getResponse().getStatus())
                    .entity(exception.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

}

