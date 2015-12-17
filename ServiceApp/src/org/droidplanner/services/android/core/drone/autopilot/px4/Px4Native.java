package org.droidplanner.services.android.core.drone.autopilot.px4;

import android.content.Context;
import android.os.Handler;

import org.droidplanner.services.android.communication.model.DataStreams;
import org.droidplanner.services.android.core.drone.DroneInterfaces;
import org.droidplanner.services.android.core.drone.LogMessageListener;
import org.droidplanner.services.android.core.drone.autopilot.generic.GenericMavLinkDrone;
import org.droidplanner.services.android.core.firmware.FirmwareType;
import org.droidplanner.services.android.core.model.AutopilotWarningParser;

/**
 * Created by Fredia Huya-Kouadio on 9/10/15.
 */
public class Px4Native extends GenericMavLinkDrone {

    public Px4Native(Context context, Handler handler, DataStreams.DataOutputStream mavClient, AutopilotWarningParser warningParser, LogMessageListener logListener, DroneInterfaces.AttributeEventListener listener) {
        super(context, handler, mavClient, warningParser, logListener, listener);
    }

    @Override
    public FirmwareType getFirmwareType() {
        return FirmwareType.PX4_NATIVE;
    }

}
