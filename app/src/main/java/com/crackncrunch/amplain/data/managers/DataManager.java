package com.crackncrunch.amplain.data.managers;

import android.content.Context;
import android.net.Uri;

import com.crackncrunch.amplain.App;
import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.network.RestService;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.data.storage.dto.UserAddressDto;
import com.crackncrunch.amplain.data.storage.dto.UserDto;
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

    private UserDto mUser;
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
        initMockUserData();
        generateMockData();
    }

    //region ==================== Users ===================

    public void loginUser(String email, String password) {
        // TODO: 23-Oct-16 implement user authentication
    }

    public boolean isAuthUser() {
        // TODO: 20-Feb-17 Check User auth token in SharedPreferences
        return true;
    }

    public Map<String, String> getUserProfileInfo() {
        return mUserProfileInfo;
    }

    public void saveProfileInfo(String name, String phone, String avatar) {
        mUserProfileInfo.put(PROFILE_FULL_NAME_KEY, name);
        mUserProfileInfo.put(PROFILE_PHONE_KEY, phone);
        mUserProfileInfo.put(PROFILE_AVATAR_KEY, avatar);
    }

    public ArrayList<UserAddressDto> getUserAddresses() {
        return mUserAddresses;
    }

    public Map<String, Boolean> getUserSettings() {
        return mUserSettings;
    }

    public void saveAvatarPhoto(Uri photoUri) {
        // mPreferencesManager.saveAvatar(photoUri.toString());
    }

    public void saveSetting(String notificationKey, boolean isChecked) {
        // TODO: 29-Nov-16 implement method
    }

    public void addAddress(UserAddressDto userAddressDto) {
        // TODO: 29-Nov-16 implement method
    }

    //endregion

    //region ==================== Products ===================

    public ProductDto getProductById(int productId) {
        // TODO: 28-Oct-16 gets product from mock (to be converted to DB)
        return mMockProductList.get(productId + 1);
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

    private void initMockUserData() {
        mUserProfileInfo = new HashMap<>();
        mUserProfileInfo.put(PROFILE_FULL_NAME_KEY, "Donald " +
                "Trump");
        mUserProfileInfo.put(PROFILE_AVATAR_KEY,
                "http://a1.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTIwNjA4NjM0MDQyNzQ2Mzgw.jpg");
        mUserProfileInfo.put(PROFILE_PHONE_KEY, "334-29-3093");

        mUserAddresses = new ArrayList<>();
        UserAddressDto userAddress;
        userAddress = new UserAddressDto(3, "Home", "Airport Road", "24", "56",
                9, "Beware of crazy dogs");
        mUserAddresses.add(userAddress);

        userAddress = new UserAddressDto(5, "Work", "Central Park", "123", "67",
                2, "In the middle of nowhere");
        mUserAddresses.add(userAddress);

        mUserSettings = new HashMap<>();
        mUserSettings.put(PreferencesManager.NOTIFICATION_ORDER_KEY, true);
        mUserSettings.put(PreferencesManager.NOTIFICATION_PROMO_KEY, false);

        mUser = new UserDto(mUserProfileInfo, mUserAddresses, mUserSettings);
    }

    private void generateMockData() {
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
}
