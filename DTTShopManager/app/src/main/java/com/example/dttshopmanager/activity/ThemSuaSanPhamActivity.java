package com.example.dttshopmanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dttshopmanager.R;
import com.example.dttshopmanager.model.MessageModel;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.retrofit.ApiQuanLyBanHang;
import com.example.dttshopmanager.retrofit.RetrofitClient;
import com.example.dttshopmanager.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSuaSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarAddEditSP;
    TextInputEditText editTxtTenSP, editTxtGiaSP, editTxtGiaKhuyenMai, editTxtHinhAnh, editTxtMoTa, editTxtSoLuong;
    ImageView imgShowPic;
    ImageButton imgBtnChosePic;
    Spinner spinnerLoai;
    AppCompatButton appCompatBtnAddEditSP;
    ApiQuanLyBanHang apiQuanLyBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean checkChoseImage = false;
    File file;
    SanPhamMoi suaSanPham;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_san_pham);

        anhXaThongTin();
        actionToolBar();
        choseImageClick();
        addDataSpinner();
        buttonAddEditClick();
        getInfoSuaSP();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        toolbarAddEditSP = findViewById(R.id.toolBarAddEditSP);
        editTxtTenSP = findViewById(R.id.editTextTenSP);
        editTxtGiaSP = findViewById(R.id.editTextGiaSP);
        editTxtGiaKhuyenMai = findViewById(R.id.editTextGiaKhuyenMai);
        editTxtHinhAnh = findViewById(R.id.editTextHinhAnhSP);
        editTxtMoTa = findViewById(R.id.editTextMoTaSP);
        editTxtSoLuong = findViewById(R.id.editTextSoLuongSP);
        imgShowPic = findViewById(R.id.imageViewShowPic);
        imgBtnChosePic = findViewById(R.id.imageButtonChosePic);
        TooltipCompat.setTooltipText(imgBtnChosePic, "Chọn hình ảnh");
        spinnerLoai = findViewById(R.id.spinnerLoai);
        appCompatBtnAddEditSP = findViewById(R.id.appCompatButtonAddEditSP);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarAddEditSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarAddEditSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void choseImageClick() {
        imgBtnChosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemSuaSanPhamActivity.this)
                .provider(ImageProvider.BOTH)
                .crop()
                .compress(1024)
                .maxResultSize(600, 600)
                .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imagePath = data.getData();
        if(imagePath != null) {
            Glide.with(getApplicationContext()).load(imagePath).into(imgShowPic);
            file = new File(imagePath.getPath());
            editTxtHinhAnh.setText(file.getName());
            editTxtHinhAnh.setEnabled(true);
            checkChoseImage = true;
        }
        else {
            if(!flag) {
                Glide.with(getApplicationContext()).load(R.drawable.no_image).into(imgShowPic);
                editTxtHinhAnh.getText().clear();
                editTxtHinhAnh.setEnabled(false);
                checkChoseImage = false;
            }
            else {
                if (suaSanPham.getHinhAnhSP().contains("http") || suaSanPham.getHinhAnhSP().contains("https"))
                    Glide.with(getApplicationContext()).load(suaSanPham.getHinhAnhSP()).into(imgShowPic);
                else {
                    String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + suaSanPham.getHinhAnhSP();
                    Glide.with(getApplicationContext()).load(urlHinhAnhSP).into(imgShowPic);
                }
                editTxtHinhAnh.setText(suaSanPham.getHinhAnhSP());
                editTxtHinhAnh.setEnabled(false);
                checkChoseImage = false;
            }
        }
    }

    private void addDataSpinner() {
        String[] loaiSP = new String[] {"Chọn loại sản phẩm", "Điện thoại", "Laptop", "Máy tính bảng"};
        ArrayAdapter<String> arrayAdapterSpinner = new ArrayAdapter<>(this, R.layout.item_spinner_centered, loaiSP);
        spinnerLoai.setAdapter(arrayAdapterSpinner);
    }

    private void buttonAddEditClick() {
        appCompatBtnAddEditSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag)
                    addDataSanPham();
                else
                    suaDataSanPham();
            }
        });
    }

    private void getInfoSuaSP() {
        suaSanPham = (SanPhamMoi) getIntent().getSerializableExtra("suasanpham");
        if(suaSanPham == null)
            flag = false;
        else {
            flag = true;
            toolbarAddEditSP.setTitle("Sửa sản phẩm");
            appCompatBtnAddEditSP.setText("Sửa sản phẩm");

            editTxtTenSP.setText(suaSanPham.getTenSP());
            editTxtGiaSP.setText(suaSanPham.getGiaSP());
            editTxtGiaKhuyenMai.setText(suaSanPham.getGiaKhuyenMai());
            editTxtHinhAnh.setText(suaSanPham.getHinhAnhSP());
            editTxtMoTa.setText(suaSanPham.getMoTa());
            editTxtSoLuong.setText(suaSanPham.getSoLuongSP() + "");
            if (suaSanPham.getHinhAnhSP().contains("http") || suaSanPham.getHinhAnhSP().contains("https"))
                Glide.with(getApplicationContext()).load(suaSanPham.getHinhAnhSP()).into(imgShowPic);
            else {
                String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + suaSanPham.getHinhAnhSP();
                Glide.with(getApplicationContext()).load(urlHinhAnhSP).into(imgShowPic);
            }
            if(suaSanPham.getLoai().equals("DT"))
                spinnerLoai.setSelection(1);
            else if(suaSanPham.getLoai().equals("LT"))
                spinnerLoai.setSelection(2);
            else if(suaSanPham.getLoai().equals("MTB"))
                spinnerLoai.setSelection(3);
        }
    }

    private void addDataSanPham() {
        String strTenSP, strGiaSP, strGiaKhuyenMai, strHinhAnh, strMoTa, strLoai, strSoLuong;
        strTenSP = editTxtTenSP.getText().toString().trim();
        strGiaSP = editTxtGiaSP.getText().toString().trim();
        strGiaKhuyenMai = editTxtGiaKhuyenMai.getText().toString().trim();
        strHinhAnh = editTxtHinhAnh.getText().toString().trim();
        strMoTa = editTxtMoTa.getText().toString().trim();
        strSoLuong = editTxtSoLuong.getText().toString().trim();

        if(spinnerLoai.getSelectedItemPosition() == 1)
            strLoai = "DT";
        else if(spinnerLoai.getSelectedItemPosition() == 2)
            strLoai = "LT";
        else if(spinnerLoai.getSelectedItemPosition() == 3)
            strLoai = "MTB";
        else
            strLoai = "";

        if(TextUtils.isEmpty(strTenSP)) {
            editTxtTenSP.setError("Không được để trống");
            editTxtTenSP.requestFocus();
        }
        else if(TextUtils.isEmpty(strGiaSP)) {
            editTxtGiaSP.setError("Không được để trống");
            editTxtGiaSP.requestFocus();
        }
        else if(strGiaSP.length() > 9) {
            editTxtGiaSP.setError("Giá sản phẩm chỉ 9 chữ số");
            editTxtGiaSP.requestFocus();
        }
        else if(!TextUtils.isEmpty(strGiaKhuyenMai)) {
            if(Integer.parseInt(strGiaKhuyenMai) >= Integer.parseInt(strGiaSP)) {
                editTxtGiaKhuyenMai.setError("Giá giảm phải nhỏ hơn giá gốc");
                editTxtGiaKhuyenMai.requestFocus();
            }
            else if(!checkChoseImage)
                Toast.makeText(getApplicationContext(), "Chưa chọn hình ảnh", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(strHinhAnh))
                Toast.makeText(getApplicationContext(), "Chưa đặt tên hình ảnh", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(strMoTa)) {
                editTxtMoTa.setError("Không được để trống");
                editTxtMoTa.requestFocus();
            }
            else if(spinnerLoai.getSelectedItemPosition() == 0)
                Toast.makeText(getApplicationContext(), "Chưa chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(strSoLuong)) {
                editTxtSoLuong.setError("Không được để trống");
                editTxtSoLuong.requestFocus();
            }
            else {
                String finalStrHinhAnh;
                if(strHinhAnh.equals(file.getName()))
                    finalStrHinhAnh = file.getName();
                else {
                    File fileRename = new File(file, strHinhAnh);
                    finalStrHinhAnh = fileRename.getName();
                }

                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", finalStrHinhAnh, requestBody);
                Call<MessageModel> call = apiQuanLyBanHang.uploadFile(fileToUpload);
                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        MessageModel serverResponse = response.body();
                        if(serverResponse.isSuccess()) {
                            compositeDisposable.add(apiQuanLyBanHang.addSP(strTenSP, strGiaSP, strGiaKhuyenMai, finalStrHinhAnh, strMoTa, strLoai , Integer.parseInt(strSoLuong))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    sanPhamMoiModel -> {
                                        if(sanPhamMoiModel.isSuccess())
                                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getApplicationContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Không thể tải lên ảnh", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                    }
                });
            }
        }
        else if(!checkChoseImage)
            Toast.makeText(getApplicationContext(), "Chưa chọn hình ảnh", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strHinhAnh))
            Toast.makeText(getApplicationContext(), "Chưa đặt tên hình ảnh", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strMoTa)) {
            editTxtMoTa.setError("Không được để trống");
            editTxtMoTa.requestFocus();
        }
        else if(spinnerLoai.getSelectedItemPosition() == 0)
            Toast.makeText(getApplicationContext(), "Chưa chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strSoLuong)) {
            editTxtSoLuong.setError("Không được để trống");
            editTxtSoLuong.requestFocus();
        }
        else {
            String finalStrHinhAnh;
            if(strHinhAnh.equals(file.getName()))
                finalStrHinhAnh = file.getName();
            else {
                File fileRename = new File(file, strHinhAnh);
                finalStrHinhAnh = fileRename.getName();
            }

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", finalStrHinhAnh, requestBody);
            Call<MessageModel> call = apiQuanLyBanHang.uploadFile(fileToUpload);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    MessageModel serverResponse = response.body();
                    if(serverResponse.isSuccess()) {
                        compositeDisposable.add(apiQuanLyBanHang.addSP(strTenSP, strGiaSP, strGiaKhuyenMai, finalStrHinhAnh, strMoTa, strLoai, Integer.parseInt(strSoLuong))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                sanPhamMoiModel -> {
                                    if(sanPhamMoiModel.isSuccess())
                                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getApplicationContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                }
                        ));
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Không thể tải lên ảnh", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {

                }
            });
        }
    }

    private void suaDataSanPham() {
        String strTenSP, strGiaSP, strGiaKhuyenMai, strHinhAnh, strMoTa, strLoai, strSoLuong;
        strTenSP = editTxtTenSP.getText().toString().trim();
        strGiaSP = editTxtGiaSP.getText().toString().trim();
        strGiaKhuyenMai = editTxtGiaKhuyenMai.getText().toString().trim();
        strHinhAnh = editTxtHinhAnh.getText().toString().trim();
        strMoTa = editTxtMoTa.getText().toString().trim();
        strSoLuong = editTxtSoLuong.getText().toString().trim();

        if(spinnerLoai.getSelectedItemPosition() == 1)
            strLoai = "DT";
        else if(spinnerLoai.getSelectedItemPosition() == 2)
            strLoai = "LT";
        else if(spinnerLoai.getSelectedItemPosition() == 3)
            strLoai = "MTB";
        else
            strLoai = "";

        if(TextUtils.isEmpty(strTenSP)) {
            editTxtTenSP.setError("Không được để trống");
            editTxtTenSP.requestFocus();
        }
        else if(TextUtils.isEmpty(strGiaSP)) {
            editTxtGiaSP.setError("Không được để trống");
            editTxtGiaSP.requestFocus();
        }
        else if(strGiaSP.length() > 9) {
            editTxtGiaSP.setError("Giá sản phẩm chỉ 9 chữ số");
            editTxtGiaSP.requestFocus();
        }
        else if(!TextUtils.isEmpty(strGiaKhuyenMai)) {
            if(Integer.parseInt(strGiaKhuyenMai) >= Integer.parseInt(strGiaSP)) {
                editTxtGiaKhuyenMai.setError("Giá giảm phải nhỏ hơn giá gốc");
                editTxtGiaKhuyenMai.requestFocus();
            }
            else if(TextUtils.isEmpty(strHinhAnh))
                Toast.makeText(getApplicationContext(), "Chưa đặt tên hình ảnh", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(strMoTa)) {
                editTxtMoTa.setError("Không được để trống");
                editTxtMoTa.requestFocus();
            }
            else if(spinnerLoai.getSelectedItemPosition() == 0)
                Toast.makeText(getApplicationContext(), "Chưa chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(strSoLuong)) {
                editTxtSoLuong.setError("Không được để trống");
                editTxtSoLuong.requestFocus();
            }
            else {
                if(checkChoseImage) {
                    String finalStrHinhAnh;
                    if(strHinhAnh.equals(file.getName()))
                        finalStrHinhAnh = file.getName();
                    else {
                        File fileRename = new File(file, strHinhAnh);
                        finalStrHinhAnh = fileRename.getName();
                    }

                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", finalStrHinhAnh, requestBody);
                    Call<MessageModel> call = apiQuanLyBanHang.uploadFile(fileToUpload);
                    call.enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            MessageModel serverResponse = response.body();
                            if (serverResponse.isSuccess()) {
                                compositeDisposable.add(apiQuanLyBanHang.editSP(suaSanPham.getIdSP(), strTenSP, strGiaSP, strGiaKhuyenMai, finalStrHinhAnh, strMoTa, strLoai, Integer.parseInt(strSoLuong))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        sanPhamMoiModel -> {
                                            if (sanPhamMoiModel.isSuccess())
                                                Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(getApplicationContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                                        },
                                        throwable -> {
                                            Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                ));
                            } else
                                Toast.makeText(getApplicationContext(), "Không thể tải lên ảnh", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {

                        }
                    });
                }
                else {
                    compositeDisposable.add(apiQuanLyBanHang.editSP(suaSanPham.getIdSP(), strTenSP, strGiaSP, strGiaKhuyenMai, strHinhAnh, strMoTa, strLoai, Integer.parseInt(strSoLuong))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanPhamMoiModel -> {
                                if (sanPhamMoiModel.isSuccess())
                                    Toast.makeText(getApplicationContext(), "Sủa thành công", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
                }
            }
        }
        else if(TextUtils.isEmpty(strHinhAnh))
            Toast.makeText(getApplicationContext(), "Chưa đặt tên hình ảnh", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strMoTa)) {
            editTxtMoTa.setError("Không được để trống");
            editTxtMoTa.requestFocus();
        }
        else if(spinnerLoai.getSelectedItemPosition() == 0)
            Toast.makeText(getApplicationContext(), "Chưa chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
        else {
            if(checkChoseImage) {
                String finalStrHinhAnh;
                if(strHinhAnh.equals(file.getName()))
                    finalStrHinhAnh = file.getName();
                else {
                    File fileRename = new File(file, strHinhAnh);
                    finalStrHinhAnh = fileRename.getName();
                }

                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", finalStrHinhAnh, requestBody);
                Call<MessageModel> call = apiQuanLyBanHang.uploadFile(fileToUpload);
                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        MessageModel serverResponse = response.body();
                        if (serverResponse.isSuccess()) {
                            compositeDisposable.add(apiQuanLyBanHang.editSP(suaSanPham.getIdSP(), strTenSP, strGiaSP, strGiaKhuyenMai, finalStrHinhAnh, strMoTa, strLoai, Integer.parseInt(strSoLuong))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    sanPhamMoiModel -> {
                                        if (sanPhamMoiModel.isSuccess())
                                            Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getApplicationContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));
                        } else
                            Toast.makeText(getApplicationContext(), "Không thể tải lên ảnh", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {

                    }
                });
            }
            else {
                compositeDisposable.add(apiQuanLyBanHang.editSP(suaSanPham.getIdSP(), strTenSP, strGiaSP, strGiaKhuyenMai, strHinhAnh, strMoTa, strLoai, Integer.parseInt(strSoLuong))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess())
                                Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
            }
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}