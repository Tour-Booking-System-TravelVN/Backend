package com.travelvn.tourbookingsytem.util;

import java.util.HashMap;

public class Decryption {
    private static final HashMap<String, String> hashMap = new HashMap<>();
    static {
        hashMap.put("001", "Thành phố Hà Nội");
        hashMap.put("002", "Hà Giang");
        hashMap.put("004", "Cao Bằng");
        hashMap.put("006", "Bắc Kạn");
        hashMap.put("008", "Tuyên Quang");
        hashMap.put("110", "Lào Cai");
        hashMap.put("111", "Điện Biên");
        hashMap.put("112", "Lai Châu");
        hashMap.put("114", "Sơn La");
        hashMap.put("115", "Yên Bái");
        hashMap.put("117", "Hòa Bình");
        hashMap.put("119", "Thái Nguyên");
        hashMap.put("220", "Lạng Sơn");
        hashMap.put("222", "Quảng Ninh");
        hashMap.put("224", "Bắc Giang");
        hashMap.put("225", "Phú Thọ");
        hashMap.put("226", "Vĩnh Phúc");
        hashMap.put("227", "Bắc Ninh");
        hashMap.put("330", "Hải Dương");
        hashMap.put("331", "Thành phố Hải Phòng");
        hashMap.put("333", "Hưng Yên");
        hashMap.put("334", "Thái Bình");
        hashMap.put("335", "Hà Nam");
        hashMap.put("336", "Nam Định");
        hashMap.put("337", "Ninh Bình");
        hashMap.put("338", "Thanh Hóa");
        hashMap.put("440", "Nghệ An");
        hashMap.put("442", "Hà Tĩnh");
        hashMap.put("444", "Quảng Bình");
        hashMap.put("445", "Quảng Trị");
        hashMap.put("446", "Thừa Thiên-Huế");
        hashMap.put("448", "Thành phố Đà Nẵng");
        hashMap.put("449", "Quảng Nam");
        hashMap.put("551", "Quảng Ngãi");
        hashMap.put("552", "Bình Định");
        hashMap.put("554", "Phú Yên");
        hashMap.put("556", "Khánh Hòa");
        hashMap.put("558", "Ninh Thuận");
        hashMap.put("660", "Bình Thuận");
        hashMap.put("662", "Kon Tum");
        hashMap.put("664", "Gia Lai");
        hashMap.put("666", "Đắk Lắk");
        hashMap.put("667", "Đắc Nông");
        hashMap.put("668", "Lâm Đồng");
        hashMap.put("770", "Bình Phước");
        hashMap.put("772", "Tây Ninh");
        hashMap.put("777", "Bình Dương");
        hashMap.put("775", "Đồng Nai");
        hashMap.put("777", "Bà Rịa-Vũng Tàu");
        hashMap.put("779", "Thành Phố Hồ Chí Minh");
        hashMap.put("880", "Long An");
        hashMap.put("882", "Tiền Giang");
        hashMap.put("883", "Bến Tre");
        hashMap.put("884", "Trà Vinh");
        hashMap.put("886", "Vĩnh Long");
        hashMap.put("887", "Đồng Tháp");
        hashMap.put("889", "An Giang");
        hashMap.put("991", "Kiên Giang");
        hashMap.put("992", "Thành phố Cần Thơ");
        hashMap.put("993", "Hậu Giang");
        hashMap.put("994", "Sóc Trăng");
        hashMap.put("995", "Bạc Liêu");
        hashMap.put("996", "Cà Mau");
    }

    //Kiểm tra 3 số đầu có hợp lệ không
    public boolean isFirstThreeDigitsValid(String input) {
        input = input.substring(0,3);
        return hashMap.containsKey(input);
    }

    //Kiem tra ngay sinh va gioi tinh co dung khong
    public boolean checkBirthOfDateAndGender(String cccd, int yearOfBirth, Boolean gender) {
        yearOfBirth -= Integer.parseInt(cccd.substring(4, 6));
        if (yearOfBirth % 100 != 0) {
            return false;
        }
        if ((yearOfBirth == 1900)) {
            if (!gender)
                return cccd.charAt(3) == '0'; //Nếu là nam
            return cccd.charAt(3) == '1'; //Nếu là nữ
        } else if (yearOfBirth == 2000) {
            if (!gender)
                return cccd.charAt(3) == '2'; //Nếu là nam
            return cccd.charAt(3) == '3'; //Nếu là nữ
        }
        return false;
    }
}
