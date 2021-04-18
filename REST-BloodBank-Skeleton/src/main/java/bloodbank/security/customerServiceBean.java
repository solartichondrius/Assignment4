package bloodbank.security;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.resource.spi.work.SecurityContext;
import javax.websocket.server.PathParam;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.glassfish.soteria.WrappingCallerPrincipal;

import bloodbank.entity.Person;
import bloodbank.entity.SecurityUser;

public class customerServiceBean {
	private static final String USER_ROLE = null;
	@Inject
	protected SecurityContext sc;
	@RolesAllowed({USER_ROLE})
	@GET
	@Path("{userId}")
	public Response getPersonById(@PathParam("id")
	 int id) {
		Response response = null;
	 	WrappingCallerPrincipal wCallerPrincipal =
			 (WrappingCallerPrincipal) sc.getCallerPrincipal();
	 	SecurityUser sUser =
	 			(SecurityUser)wCallerPrincipal.getWrapped();
	 	Person c = sUser.getPerson();
	 	if (c!=null && c.getId() != id) {
	 		throw new ForbiddenException(
	 				"User trying to access resource it does not own" +
	 				"(wrong userid)");
	 	}
	 // ...
	 return response;
	}
}
