package controller.commands.pageconstructor.factories;

import controller.commands.pageconstructor.pages.Page;
import controller.commands.pageconstructor.pages.ProductPage;
import controller.commands.pageconstructor.roles.Role;
import controller.commands.pageconstructor.roles.UserRole;

public class UserProductsFactory implements PageConstructorFactory {
    @Override
    public Role createRole() {
        return new UserRole();
    }

    @Override
    public Page createPage() {
        return new ProductPage();
    }

    @Override
    public String createPagePath() {
        return createRole().request() + createPage().request();
    }
}
