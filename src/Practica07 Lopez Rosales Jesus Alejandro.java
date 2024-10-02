import javax.swing.*;
import java.awt.*;

class Mandala extends JFrame {
    private JPanel panelDibujo;

    public Mandala() {
        setTitle("Mandala");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarMandala(g);
            }
        };
        panelDibujo.setBackground(Color.WHITE);
        add(panelDibujo);
    }

    private void dibujarMandala(Graphics g) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g.setColor(colorRandom());
        dibujarCirculo(g, centerX, centerY, 300);

        g.setColor(colorRandom());
        dibujarCuadrado(g, centerX - 425 / 2, centerY - 425 / 2, 425);

        g.setColor(colorRandom());
        dibujarCuadrado(g, centerX - 300 / 2, centerY - 300 / 2, 300);

        g.setColor(colorRandom());
        dibujarCirculo(g, centerX, centerY, 300 / 2);

        g.setColor(colorRandom());
        dibujarOvalo(g, centerX, centerY, 300, 150);

        g.setColor(colorRandom());
        dibujarOvalo(g, centerX, centerY, 150, 300);

        g.setColor(colorRandom());
        for (int i = 0; i < 360; i += 15) {
            double radianes = Math.toRadians(i);
            int x = (int) (centerX + 150 * Math.cos(radianes));
            int y = (int) (centerY + 150 * Math.sin(radianes));
            dibujarLinea(g, centerX, centerY, x, y);
        }

        g.setColor(colorRandom());
        for (int i = 0; i < 360; i += 60) {
            double radianes = Math.toRadians(i);
            int x = (int) (centerX + 240 * Math.cos(radianes));
            int y = (int) (centerY + 240 * Math.sin(radianes));
            dibujarCirculo(g, x, y, 30);
        }

        g.setColor(colorRandom());
        for (int i = 0; i < 360; i += 90) {
            double radianes = Math.toRadians(i);
            int x = (int) (centerX + 300 * Math.cos(radianes));
            int y = (int) (centerY + 300 * Math.sin(radianes));
            dibujarRectangulo(g, x - 50 / 2, y - 100 / 2, 50, 100);
        }
    }

    private Color colorRandom() {
        int r = (int) (Math.random() * 128) + 128;
        int g = (int) (Math.random() * 128) + 128;
        int b = (int) (Math.random() * 128) + 128;
        return new Color(r, g, b);
    }

    private void putPixel(Graphics g, int x, int y) {
        g.drawLine(x, y, x, y);
    }

    private void dibujarLinea(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(g, x1, y1);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private void dibujarCuadrado(Graphics g, int x, int y, int lado) {
        dibujarRectangulo(g, x, y, lado, lado);
    }

    private void dibujarRectangulo(Graphics g, int x, int y, int ancho, int alto) {
        dibujarLinea(g, x, y, x + ancho, y);
        dibujarLinea(g, x + ancho, y, x + ancho, y + alto);
        dibujarLinea(g, x + ancho, y + alto, x, y + alto);
        dibujarLinea(g, x, y + alto, x, y);
    }

    private void dibujarCirculo(Graphics g, int x0, int y0, int radio) {
        int x = radio;
        int y = 0;
        int err = 0;

        while (x >= y) {
            putPixel(g, x0 + x, y0 + y);
            putPixel(g, x0 + y, y0 + x);
            putPixel(g, x0 - y, y0 + x);
            putPixel(g, x0 - x, y0 + y);
            putPixel(g, x0 - x, y0 - y);
            putPixel(g, x0 - y, y0 - x);
            putPixel(g, x0 + y, y0 - x);
            putPixel(g, x0 + x, y0 - y);

            y += 1;
            err += 1 + 2 * y;
            if (2 * (err - x) + 1 > 0) {
                x -= 1;
                err += 1 - 2 * x;
            }
        }
    }

    private void dibujarOvalo(Graphics g, int x, int y, int a, int b) {
        int a2 = a * a;
        int b2 = b * b;
        int fa2 = 4 * a2, fb2 = 4 * b2;
        int x1, y1, sigma;

        /* first half */
        for (x1 = 0, y1 = b, sigma = 2 * b2 + a2 * (1 - 2 * b); b2 * x1 <= a2 * y1; x1++) {
            putPixel(g, x + x1, y + y1);
            putPixel(g, x - x1, y + y1);
            putPixel(g, x + x1, y - y1);
            putPixel(g, x - x1, y - y1);
            if (sigma >= 0) {
                sigma += fa2 * (1 - y1);
                y1--;
            }
            sigma += b2 * ((4 * x1) + 6);
        }

        /* second half */
        for (x1 = a, y1 = 0, sigma = 2 * a2 + b2 * (1 - 2 * a); a2 * y1 <= b2 * x1; y1++) {
            putPixel(g, x + x1, y + y1);
            putPixel(g, x - x1, y + y1);
            putPixel(g, x + x1, y - y1);
            putPixel(g, x - x1, y - y1);
            if (sigma >= 0) {
                sigma += fb2 * (1 - x1);
                x1--;
            }
            sigma += a2 * ((4 * y1) + 6);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Mandala().setVisible(true));
    }
}