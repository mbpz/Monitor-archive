package org.doug.monitor.bluetooth.conn;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * callback of {@link BluetoothGattCharacteristic} operation.
 */
public abstract class BleCharactCallback extends BleCallback {
    public abstract void onSuccess(BluetoothGattCharacteristic characteristic);
}