package com.crackncrunch.amplain.data.managers;

import android.content.Context;

import com.crackncrunch.amplain.App;
import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.network.RestService;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.data.storage.dto.UserAddressDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.components.DaggerDataManagerComponent;
import com.crackncrunch.amplain.di.components.DataManagerComponent;
import com.crackncrunch.amplain.di.modules.LocalModule;
import com.crackncrunch.amplain.di.modules.NetworkModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.crackncrunch.amplain.data.managers.PreferencesManager.PROFILE_AVATAR_KEY;
import static com.crackncrunch.amplain.data.managers.PreferencesManager.PROFILE_FULL_NAME_KEY;
import static com.crackncrunch.amplain.data.managers.PreferencesManager.PROFILE_PHONE_KEY;

/**
 * Created by Lilian on 20-Feb-17.
 */

public class DataManager {

    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;
    @Inject
    Context mContext;

    private List<ProductDto> mMockProductList;
    private Map<String, String> mUserProfileInfo;
    private ArrayList<UserAddressDto> mUserAddresses;
    private Map<String, Boolean> mUserSettings;

    public DataManager() {
        DataManagerComponent component = DaggerService.getComponent
                (DataManagerComponent.class);
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);

        generateProductsMockData();
        initUserProfileData();
        initUserSettingsData();
        initAddressData();
    }

    //region ==================== User Profile ===================

    private void initUserProfileData() {
        mUserProfileInfo = new HashMap<>();

        mUserProfileInfo = mPreferencesManager.getUserProfileInfo();
        if (mUserProfileInfo.get(PROFILE_FULL_NAME_KEY).equals("")) {
            mUserProfileInfo.put(PROFILE_FULL_NAME_KEY, "Hulk Hogan");
        }
        if (mUserProfileInfo.get(PROFILE_AVATAR_KEY).equals("")) {
            mUserProfileInfo.put(PROFILE_AVATAR_KEY,
                    "http://a1.files.biography.com/image/upload/c_fill,cs_srgb," +
                            "dpr_1.0,g_face,h_300,q_80,w_300/MTIwNjA4NjM0MDQyNzQ2Mzgw.jpg");
        }
        if (mUserProfileInfo.get(PROFILE_PHONE_KEY).equals("")) {
            mUserProfileInfo.put(PROFILE_PHONE_KEY, "+7(917)971-38-27");
        }
    }

    public Map<String, String> getUserProfileInfo() {
        return mUserProfileInfo;
    }

    public void saveUserProfileInfo(String name, String phone, String avatar) {
        mUserProfileInfo.put(PROFILE_FULL_NAME_KEY, name);
        mUserProfileInfo.put(PROFILE_PHONE_KEY, phone);
        mUserProfileInfo.put(PROFILE_AVATAR_KEY, avatar);
        mPreferencesManager.saveProfileInfo(mUserProfileInfo);
    }

    //endregion

    //region ==================== Users ===================

    public void loginUser(String email, String password) {
        // TODO: 23-Oct-16 implement user authentication
    }

    public boolean isAuthUser() {
        // TODO: 20-Feb-17 Check User auth token in SharedPreferences
        return true;
    }

    private void initUserSettingsData() {
        mUserSettings = mPreferencesManager.getUserSettings();
    }

    public Map<String, Boolean> getUserSettings() {
        return mUserSettings;
    }

    public void saveSetting(String notificationKey, boolean isChecked) {
        mPreferencesManager.saveSetting(notificationKey, isChecked);
        mUserSettings.put(notificationKey, isChecked);
    }

    //endregion

    //region ==================== Addresses ===================

    public ArrayList<UserAddressDto> getUserAddresses() {
        return mUserAddresses;
    }

    public void updateOrInsertAddress(UserAddressDto addressDto) {
        if (mUserAddresses.contains(addressDto)) {
            mUserAddresses.set(mUserAddresses.indexOf(addressDto), addressDto);
        } else {
            mUserAddresses.add(0, addressDto);
        }
    }

    public void removeAddress(UserAddressDto addressDto) {
        if (mUserAddresses.contains(addressDto)) {
            mUserAddresses.remove(mUserAddresses.indexOf(addressDto));
        }
    }

    //endregion

    //region ==================== Products ===================

    public ProductDto getProductById(int productId) {
        // TODO: 28-Oct-16 gets product from mock (to be converted to DB)
        return mMockProductList.get(productId - 1);
    }

    public List<ProductDto> getProductList() {
        // TODO: 28-Oct-16 get product list
        return mMockProductList;
    }

    public void updateProduct(ProductDto product) {
        // TODO: 28-Oct-16 update product count or other property and save to DB
    }

    //endregion

    private String getResVal(int resourceId) {
        return mContext.getString(resourceId);
    }

    //region ==================== Data Initialization ===================

    private void initAddressData() {
        mUserAddresses = new ArrayList<>();
        UserAddressDto userAddress;
        userAddress = new UserAddressDto("1", "Home", "Airport Road",
                "24", "56", 9, "Beware of crazy dogs");
        mUserAddresses.add(userAddress);

        userAddress = new UserAddressDto("2", "Work", "Central Park",
                "123", "67", 2, "In the middle of nowhere");
        mUserAddresses.add(userAddress);
    }

    private void generateProductsMockData() {
        mMockProductList = new ArrayList<>();
        mMockProductList.add(new ProductDto(1,
                getResVal(R.string.product_name_1),
                getResVal(R.string.product_url_1),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(2,
                getResVal(R.string.product_name_2),
                getResVal(R.string.product_url_2),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(3,
                getResVal(R.string.product_name_3),
                getResVal(R.string.product_url_3),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(4,
                getResVal(R.string.product_name_4),
                getResVal(R.string.product_url_4),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(5,
                getResVal(R.string.product_name_5),
                getResVal(R.string.product_url_5),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(6,
                getResVal(R.string.product_name_6),
                getResVal(R.string.product_url_6),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(7,
                getResVal(R.string.product_name_7),
                getResVal(R.string.product_url_7),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(8,
                getResVal(R.string.product_name_8),
                getResVal(R.string.product_url_8),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(9,
                getResVal(R.string.product_name_9),
                getResVal(R.string.product_url_9),
                getResVal(R.string.lorem_ipsum), 100, 1));
        mMockProductList.add(new ProductDto(10,
                getResVal(R.string.product_name_10),
                getResVal(R.string.product_url_10),
                getResVal(R.string.lorem_ipsum), 100, 1));
    }

    //endregion

}
