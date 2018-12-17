package app.qsassist;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;

public class AssistantTileService extends TileService {

    @Override
    public void onStartListening() {
        super.onStartListening();

        Tile tile = getQsTile();
        tile.setState(Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        try {
            Intent dismissNotificationShade = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            this.getApplicationContext().sendBroadcast(dismissNotificationShade);
            SearchManager.class.getMethod("launchAssist", Bundle.class).invoke(searchManager, new Object[]{new Bundle()});
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
