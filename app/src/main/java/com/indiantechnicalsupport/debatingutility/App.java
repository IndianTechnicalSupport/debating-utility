package com.indiantechnicalsupport.debatingutility;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightContrastIJTheme;

public class App {

    public static void main(String[] args) {
        // Setup UI Elements and Themes
        FlatAtomOneLightContrastIJTheme.installLafInfo();
        FlatAtomOneDarkContrastIJTheme.installLafInfo();
        FlatLightLaf.setup(new FlatAtomOneDarkContrastIJTheme());

        // Create and initialise controller
        Controller director = new Controller();
        director.initialise();
    }

}
