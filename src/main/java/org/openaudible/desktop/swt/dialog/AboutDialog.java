package org.openaudible.desktop.swt.dialog;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.openaudible.desktop.swt.gui.GUI;
import org.openaudible.desktop.swt.manager.Version;
import org.openaudible.desktop.swt.manager.views.GridComposite;
import org.openaudible.desktop.swt.util.shop.FontShop;
import org.openaudible.desktop.swt.util.shop.PaintShop;



/**
 * Class displays a splash screen with info
 */
public class AboutDialog extends Window implements Version, Listener {
    final static String splashname = "48x48.png";
    Color bgColor = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
    Image splashImage = null;

    private AboutDialog(Shell parentShell) {
        super(parentShell);
    }

    public static void doAbout(Shell parent) {
        final Display display = Display.getCurrent();
        AboutDialog ab = new AboutDialog(parent);
        ab.open();
    }


    protected int getShellStyle()
    {
        return SWT.NONE;
    }

    protected void initializeBounds() {
        super.initializeBounds();
        getShell().setBackground(bgColor);
        getShell().addListener(SWT.MouseDown, this);
        Rectangle r = GUI.shell.getBounds();
        getShell().setLocation(r.x + 160, r.y + 160);
        getShell().addListener(SWT.Deactivate, this);

    }

    protected Control createContents(Composite parent) {
        GridComposite c = new GridComposite(parent, SWT.NONE);
        c.initLayout();
        splashImage = PaintShop.getImage(splashname);
        c.newImage(splashImage);
        c.addListener(SWT.MouseDown, this);
        String copyright = (char) 169 + " " + COPYRIGHT_YEAR + " All Rights Reserved";
        String build = "Build " + Version.MAJOR_VERSION+" " + INT_VERSION;

        c.newLabel(Version.longAppName).setFont(FontShop.dialogFontBold());
        c.newLabel(build).setFont(FontShop.dialogFont());
        // c.newLabel(copyright).setFont(FontShop.dialogFont());

        Link link = new Link(c, SWT.NONE);
        link.setText("https://github.com/openaudible");
        link.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                String u = event.text;
                System.out.println(u);

            }
        });
        c.newLabel("");

        c.newLabel("Not affiliated with audible.com").setFont(FontShop.dialogFont());
        return null;
    }

    public void handleEvent(Event event) {
        switch (event.type) {
            case SWT.MouseDown:
            case SWT.Deactivate:
                this.close();
                Display.getCurrent().wake();
                break;
            case SWT.Dispose:
                PaintShop.disposeImage(splashname);
                break;
            default:
                break;
        }
    }
}