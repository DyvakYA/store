package testController;

import controller.commands.Command;
import controller.servlet.CommandHolder;
import controller.servlet.MainController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static model.constants.UrlHolder.REDIRECTED;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by User on 4/24/2017.
 */
public class TestFrontController {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    CommandHolder commandHolder;
    @Mock
    Command command;

    private MainController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new MainController();
        controller.setCommandHolder(commandHolder);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(1);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetDoNothingIfCommandReturnedRedirected() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(commandHolder.findCommand(any())).thenReturn(command);
        when(command.execute(request, response)).thenReturn(REDIRECTED);
        controller.doGet(request, response);
        verify(response, times(0)).sendRedirect(any());
        verify(requestDispatcher, times(0)).forward(request, response);
    }

    @Test
    public void testDoPostDoNothingIfCommandReturnedRedirected() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(commandHolder.findCommand(any())).thenReturn(command);
        when(command.execute(request, response)).thenReturn(REDIRECTED);
        controller.doPost(request, response);
        verify(response, times(0)).sendRedirect(any());
        verify(requestDispatcher, times(0)).forward(request, response);
    }

    @Test
    public void testDoGetForwardsToReturnedByCommandPath() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(commandHolder.findCommand("GET:path")).thenReturn(command);
        when(command.execute(request, response)).thenReturn("pagePost");
        controller.doGet(request, response);
        verify(request).getRequestDispatcher("pagePost");
    }

    @Test
    public void testDoPostForwardsToReturnedByCommandPath()throws IOException, ServletException  {
        when(request.getParameter("command")).thenReturn("path");
        when(request.getMethod()).thenReturn("post");
        when(commandHolder.findCommand("POST:path")).thenReturn(command);
        when(command.execute(request, response)).thenReturn("pagePost");
        controller.doPost(request, response);
        verify(request).getRequestDispatcher("pagePost");
    }

    @Test
    public void testDoGetRetrievesGetCommandsFromHolderIfRequestMethodIsGet() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("path");
        when(request.getMethod()).thenReturn("get");
        when(commandHolder.findCommand("GET:path")).thenReturn(command);
        when(command.execute(request, response)).thenReturn("pageGet");
        controller.doGet(request, response);
        verify(commandHolder, times(1)).findCommand("GET:path");
        verify(commandHolder, times(0)).findCommand("POST:path");
    }

    @Test
    public void testDoPostRetrievesPostCommandsFromHolderIfRequestMethodIsPost() throws IOException, ServletException {
        when(request.getParameter("command")).thenReturn("path");
        when(request.getMethod()).thenReturn("post");
        when(commandHolder.findCommand("POST:path")).thenReturn(command);
        when(command.execute(request, response)).thenReturn("pagePost");
        controller.doPost(request, response);
        verify(commandHolder, times(0)).findCommand("GET:path");
        verify(commandHolder, times(1)).findCommand("POST:path");
    }

}



