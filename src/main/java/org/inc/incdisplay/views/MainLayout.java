package org.inc.incdisplay.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Route("")
public class MainLayout extends AppLayout {

    private H4 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.NAVBAR);
        addHeaderContent();
        addDrawerContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();

        viewTitle = new H4();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(false, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("INC Display 2.0");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Number Pad", NumberPadView.class, LineAwesomeIcon.KEYBOARD.create()));
        nav.addItem(new SideNavItem("Text Pad", TextPadView.class, LineAwesomeIcon.TV_SOLID.create()));
        nav.addItem(new SideNavItem("Settings", SettingsView.class, LineAwesomeIcon.COGS_SOLID.create()));
        nav.addItem(new SideNavItem("Display", DisplayView.class, LineAwesomeIcon.IMAGE.create()));

        return nav;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
