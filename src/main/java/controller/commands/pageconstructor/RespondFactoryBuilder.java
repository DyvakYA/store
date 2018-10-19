package controller.commands.pageconstructor;

import javax.servlet.http.HttpServletRequest;

public class RespondFactoryBuilder {

    private HttpServletRequest request;

    private String page;

    public RespondFactoryBuilder request(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public RespondFactoryBuilder page(String page) {
        this.page = page;
        return this;
    }

    public RespondFactory build() {
        return new RespondFactory(request, page);
    }
}
