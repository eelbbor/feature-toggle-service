package com.featuretoggle.controller;

import com.featuretoggle.domain.Account;
import com.featuretoggle.domain.dto.AccountDTO;
import com.featuretoggle.service.AccountService;
import com.featuretoggle.service.exception.ServiceInvalidInputException;
import com.featuretoggle.service.exception.ServicePersistenceException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path(AccountController.PATH_QUALIFIER)
public class AccountController extends Controller {
    public static final String PATH_QUALIFIER = "/accounts/";
    private AccountService service = new AccountService();

    @GET
    @Path(ID_PATH)
    @Override
    public Response query(@PathParam("id") String id) {
        try {
            Account account = service.queryAccount(UUID.fromString(id));
            return getOkResponse(account == null ? null : new AccountDTO(account));
        } catch (ServiceInvalidInputException e) {
            return getServerErrorResponse(e);
        } catch (ServicePersistenceException e) {
            return getServerErrorResponse(e);
        }
    }

    @POST
    public Response create(AccountDTO accountDTO) {
        try {
            Account account = service.createAccount(accountDTO.getName());
            return getOkResponse(account == null ? null : new AccountDTO(account));
        } catch (ServicePersistenceException e) {
            return getServerErrorResponse(e);
        } catch (ServiceInvalidInputException e) {
            return getServerErrorResponse(e);
        }
    }
}
