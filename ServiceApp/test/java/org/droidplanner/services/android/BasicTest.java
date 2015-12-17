package org.droidplanner.services.android;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.enums.MAV_CMD;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;

import org.droidplanner.services.android.communication.model.DataStreams;
import org.droidplanner.services.android.core.MAVLink.MavLinkCommands;
import org.droidplanner.services.android.core.drone.DroneInterfaces;
import org.droidplanner.services.android.core.drone.LogMessageListener;
import org.droidplanner.services.android.core.drone.autopilot.MavLinkDrone;
import org.droidplanner.services.android.core.drone.autopilot.apm.ArduCopter;
import org.droidplanner.services.android.mock.MockMAVLinkClient;
import org.droidplanner.services.android.utils.AndroidApWarningParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by djmedina on 3/5/15.
 * This is a simple test case.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18)
public class BasicTest {

    private MavLinkDrone drone;
    private MockMAVLinkClient mavClient;

    private final Handler dpHandler = new Handler();

    private final DataStreams.DataInputStream inputStreamListener = new DataStreams.DataInputStream() {
        @Override
        public void notifyStartingConnection() {
        }

        @Override
        public void notifyConnected() {
        }

        @Override
        public void notifyDisconnected() {
        }

        @Override
        public void notifyReceivedData(Object packet) {
        }

        @Override
        public void onStreamError(String errorMsg) {
        }
    };

    @Before
    public void setUp() throws Exception {
        final Context context = RuntimeEnvironment.application.getApplicationContext();

        ConnectionParameter connParams = new ConnectionParameter(0, new Bundle(), null);
        mavClient = new MockMAVLinkClient(context, inputStreamListener, connParams);

        drone = new ArduCopter(context, mavClient, dpHandler, new AndroidApWarningParser(), new LogMessageListener() {
            @Override
            public void onMessageLogged(int logLevel, String message) {

            }
        }, new DroneInterfaces.AttributeEventListener() {
            @Override
            public void onAttributeEvent(String attributeEvent, Bundle eventInfo, boolean checkForSololinkApi) {

            }
        });
    }

    /**
     * The name 'test preconditions' is a convention to signal that if this
     * test doesn't pass, the test case was not set up properly and it might
     * explain any and all failures in other tests.  This is not guaranteed
     * to run before other tests, as junit uses reflection to find the tests.
     */
    @Test
    public void testPreconditions() {
    }

    /**
     * Basic MAVLink message test
     */
    @Test
    public void testArm() {
        MavLinkCommands.sendArmMessage(drone, true, false, null);
        MAVLinkPacket data = mavClient.getData();
        Assert.assertTrue(data != null);

        //Unpack the message into the right MAVLink message type
        MAVLinkMessage msg = data.unpack();
        msg_command_long longMsg = (msg_command_long) msg;

        //Validate the message
        Assert.assertEquals(MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM, longMsg.command);
        Assert.assertEquals(1f, longMsg.param1, 0.001);
    }
}
