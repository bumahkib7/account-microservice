package com.codeninja.controllers;

import com.codeninja.models.Account;
import com.codeninja.models.AccountStatus;
import org.jboss.resteasy.reactive.common.jaxrs.ResponseImpl;
import org.jboss.resteasy.reactive.common.jaxrs.StatusTypeImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class accountsResourceTest {


    /**
     * Method under test: {@link accountsResource.AccountNotFoundExceptionMapper#toResponse(WebApplicationException)}
     */
    @Test
    void testAccountNotFoundExceptionMapperToResponse() {
        // Arrange
        accountsResource.AccountNotFoundExceptionMapper accountNotFoundExceptionMapper = new accountsResource.AccountNotFoundExceptionMapper();

        // Act
        Response actualToResponseResult = accountNotFoundExceptionMapper.toResponse(new WebApplicationException());

        // Assert
        assertTrue(actualToResponseResult.getStatusInfo() instanceof StatusTypeImpl);
        assertEquals("HTTP 500 Internal Server Error", actualToResponseResult.getEntity());
        assertEquals(500, actualToResponseResult.getStatus());
        assertNull(actualToResponseResult.getLanguage());
        assertNull(((ResponseImpl) actualToResponseResult).getEntityAnnotations());
    }

    /**
     * Method under test: {@link accountsResource#initSetUp()}
     */
    @Test
    void testInitSetUp() {
        // Arrange
        accountsResource accountsResource = new accountsResource();

        // Act
        accountsResource.initSetUp();

        // Assert
        assertEquals(3, accountsResource.accounts.size());
    }

    /**
     * Method under test: {@link accountsResource#getAccount(Long)}
     */
    @Test
    void testGetAccount() {
        // Arrange, Act and Assert
        assertThrows(WebApplicationException.class, () -> (new accountsResource()).getAccount(1234567890L));
    }

    /**
     * Method under test: {@link accountsResource#withdrawFunds(Long, BigDecimal)}
     */
    @Test
    void testWithdrawFunds() {
        // Arrange
        accountsResource accountsResource = new accountsResource();

        // Act and Assert
        assertThrows(WebApplicationException.class,
                () -> accountsResource.withdrawFunds(1234567890L, BigDecimal.valueOf(42L)));
    }

    /**
     * Method under test: {@link accountsResource#createNewAccount(Account)}
     */
    @Test
    void testCreateNewAccount() {
        // Arrange
        accountsResource accountsResource = new accountsResource();
        Account account = new Account();

        // Act
        Account actualCreateNewAccountResult = accountsResource.createNewAccount(account);

        // Assert
        assertSame(account, actualCreateNewAccountResult);
        assertEquals(2L, actualCreateNewAccountResult.getAccountNumber().longValue());
        assertEquals(1, accountsResource.accounts.size());
    }

    /**
     * Method under test: {@link accountsResource#createNewAccount(Account)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateNewAccount2() {
        //given
        accountsResource accountsResource = new accountsResource();
        Account account = new Account();
        account.setAccountNumber(11117L);
        account.setCustomerName("Customer Name");
        account.setCustomerNumber(99L);
        account.setBalance(BigDecimal.valueOf(56L));
        account.setAccountStatus(AccountStatus.OPEN);

        //when
        Account actualCreateNewAccountResult = accountsResource.createNewAccount(account);

        //then
        assertSame(account, actualCreateNewAccountResult);
        assertEquals(67L, actualCreateNewAccountResult.getAccountNumber().longValue());
        assertEquals(1, accountsResource.accounts.size());
        assertFalse(accountsResource.accounts.contains(account));


    }

    /**
     * Method under test: {@link accountsResource#createNewAccount(Account)}
     */
    @Test
    void testCreateNewAccount3() {
        // Arrange
        accountsResource accountsResource = new accountsResource();

        // Act and Assert
        assertThrows(WebApplicationException.class, () -> accountsResource
                .createNewAccount(new Account(1234567890L, 59L, "9.9.9.9", BigDecimal.valueOf(42L), AccountStatus.OPEN)));
    }

    /**
     * Method under test: {@link accountsResource#createNewAccount(Account)}
     */
    @Test
    void testCreateNewAccount4() {
        // Arrange
        accountsResource accountsResource = new accountsResource();

        // Act and Assert
        assertThrows(WebApplicationException.class, () -> accountsResource
                .createNewAccount(new Account(1234567890L, 59L, "9.9.9.9", BigDecimal.valueOf(42L), null)));
    }

    /**
     * Method under test: {@link accountsResource#depositFunds(Long, BigDecimal)}
     */
    @Test
    void testDepositFunds() {
        // Arrange
        accountsResource accountsResource = new accountsResource();

        // Act and Assert
        assertThrows(WebApplicationException.class,
                () -> accountsResource.depositFunds(1234567890L, BigDecimal.valueOf(42L)));
    }

    /**
     * Method under test: {@link accountsResource#deleteAccount(Long)}
     */
    @Test
    void testDeleteAccount() {
        // Arrange, Act and Assert
        assertThrows(WebApplicationException.class, () -> (new accountsResource()).deleteAccount(1234567890L));
    }
}

