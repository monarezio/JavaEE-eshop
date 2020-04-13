package cz.kodytek.shop.domain.common.utils;

import javax.enterprise.context.ApplicationScoped;
import java.awt.*;
import java.awt.image.BufferedImage;

@ApplicationScoped
public class BufferedImageUtil {

    public BufferedImage resize(BufferedImage img, int newW) {
        if (img.getWidth() > newW) {

            float scale = (float) newW / img.getWidth();
            int newH = (int) (img.getHeight() * scale);

            System.out.println("new W: " + newW);
            System.out.println("new H: " + newH);

            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            return dimg;
        }
        return img;
    }

}
