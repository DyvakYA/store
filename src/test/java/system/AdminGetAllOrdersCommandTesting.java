package system;

import controller.commands.order.AdminGetAllOrdersCommand;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AdminGetAllOrdersCommandTesting {

    private AdminGetAllOrdersCommand command = new AdminGetAllOrdersCommand();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void AdminGetAllOrdersCommand() throws IOException {

        String destinationPath = command.execute(request, response);
        assertEquals("/admin/order", destinationPath);


    }


}
