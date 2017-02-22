package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.managers.PreferencesManager;
import com.crackncrunch.amplain.data.storage.dto.UserAddressDto;
import com.crackncrunch.amplain.data.storage.dto.UserDto;

import java.util.ArrayList;
import java.util.Map;

public class AccountModel extends AbstractModel {

    public UserDto getUserDto() {
        return new UserDto(getUserProfileInfo(), getUserAddresses(), getUserSettings());
    }

    private Map<String, String> getUserProfileInfo() {
        return mDataManager.getUserProfileInfo();
    }

    private ArrayList<UserAddressDto> getUserAddresses() {
        return mDataManager.getUserAddresses();
    }

    private Map<String, Boolean> getUserSettings() {
        return mDataManager.getUserSettings();
    }

    public void saveProfileInfo(String name, String phone, String avatar) {
        mDataManager.saveProfileInfo(name, phone, avatar);
    }

    public void savePromoNotification(boolean isChecked) {
        mDataManager.saveSetting(PreferencesManager.NOTIFICATION_PROMO_KEY, isChecked);
    }

    public void saveOrderNotification(boolean isChecked) {
        mDataManager.saveSetting(PreferencesManager.NOTIFICATION_ORDER_KEY,
                isChecked);
    }

    public void addAddress(UserAddressDto userAddressDto) {
        mDataManager.addAddress(userAddressDto);
    }

    // TODO: 29-Nov-16 remove address
}
