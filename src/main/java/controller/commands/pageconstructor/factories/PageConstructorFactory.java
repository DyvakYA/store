package controller.commands.pageconstructor.factories;

import controller.commands.pageconstructor.pages.Page;
import controller.commands.pageconstructor.roles.Role;

public interface PageConstructorFactory {

    Role createRole();

    Page createPage();

    String createPagePath();


}
