package utils;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

public final class JavaFXRuntime {

    private static boolean daKhoiTao = false;

    private JavaFXRuntime() {
    }

    private static synchronized void khoiTao() {
        if (daKhoiTao) {
            return;
        }

        Runnable taoJavaFX = () -> {
            new JFXPanel();
        };

        try {
            if (SwingUtilities.isEventDispatchThread()) {
                taoJavaFX.run();
            } else {
                SwingUtilities.invokeAndWait(taoJavaFX);
            }

            daKhoiTao = true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Không thể khởi tạo JavaFX.", e);

        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Lỗi khởi tạo JavaFX.", e.getCause());
        }
    }

    public static void chay(Runnable hanhDong) {
        khoiTao();

        Platform.runLater(() -> {
            Platform.setImplicitExit(false);
            hanhDong.run();
        });
    }
}