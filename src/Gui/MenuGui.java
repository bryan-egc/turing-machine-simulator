package Gui;

import Logic.IController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Created by Eno on 29/06/2017.
 */
public class MenuGui {
    private GuiManager guiManager;
    private MenuBar menuBar;
    private MenuItem newMenu;
    private MenuItem openMenu;
    private MenuItem saveMenu;
    private MenuItem aboutMenu;
    private MenuItem exitMenu;

    public MenuGui(GuiManager guiManager) {
        setGuiManager(guiManager);
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public MenuBar getMenuBar() {
        if (menuBar == null) {
            Menu menu = new Menu("Menu");
            menu.getItems().addAll(getNewMenu(), getOpenMenu(), getSaveMenu(), getAboutMenu(), getExitMenu());
            menuBar = new MenuBar(menu);
        }
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public MenuItem getNewMenu() {
        if (newMenu == null) {
            newMenu = new MenuItem("New Turing Machine");
            newMenu.setAccelerator(KeyCombination.keyCombination("Alt+N"));
            newMenu.setOnAction(e -> {
                getGuiManager().reset();
            });
        }
        return newMenu;
    }

    public void setNewMenu(MenuItem newMenu) {
        this.newMenu = newMenu;
    }

    public MenuItem getOpenMenu() {
        if (openMenu == null) {
            openMenu = new MenuItem("Open Turing Machine");
            openMenu.setAccelerator(KeyCombination.keyCombination("Alt+O"));
            openMenu.setOnAction(e -> {
                getGuiManager().openInputsFromFile();
            });
        }
        return openMenu;
    }

    public void setOpenMenu(MenuItem openMenu) {
        this.openMenu = openMenu;
    }

    public MenuItem getSaveMenu() {
        if (saveMenu == null) {
            saveMenu = new MenuItem("Save Turing Machine");
            saveMenu.setAccelerator(KeyCombination.keyCombination("Alt+S"));
            saveMenu.setOnAction(e -> {
                getGuiManager().saveInputsOnFile();
            });
        }
        return saveMenu;
    }

    public void setSaveMenu(MenuItem saveMenu) {
        this.saveMenu = saveMenu;
    }

    public MenuItem getAboutMenu() {
        if (aboutMenu == null) {
            aboutMenu = new MenuItem("About");
            aboutMenu.setAccelerator(KeyCombination.keyCombination("Alt+A"));
            aboutMenu.setOnAction(e -> {
                new AboutGui();
            });
        }
        return aboutMenu;
    }

    public void setAboutMenu(MenuItem aboutMenu) {
        this.aboutMenu = aboutMenu;
    }

    public MenuItem getExitMenu() {
        if (exitMenu == null) {
            exitMenu = new MenuItem("Exit");
            exitMenu.setAccelerator(KeyCombination.keyCombination("Alt+E"));
            exitMenu.setOnAction(e -> {
                System.exit(0);
            });
        }
        return exitMenu;
    }

    public void setExitMenu(MenuItem exitMenu) {
        this.exitMenu = exitMenu;
    }

}
