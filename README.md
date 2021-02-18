# AppITMC

![Authors](https://play-lh.googleusercontent.com/IhsJHKS8SFOoXYptYQTJ9-PEnfsLRNTneIi3frtVMpQV1aChXmkUqAAblrnPThvYmD8=w1366-h657-rw)


### Thực Hiện:
- **Hồ Minh Quốc Bảo** 

### Nội Dung
#### I. Yêu cầu
- **Chọn môn, đề thi cần làm:**
    - Cho phép người dùng chọn môn học, chọn các đề thi cần ôn luyện để làm kiểm tra.
- **Tải đề thi:**
    - Tải các đề thi để có thể thi offline.
- **Nộp bài, xem các lại các đáp án đã chọn:**
    - Người dùng nộp bài, xem điểm và xem lại các đáp án đã chọn.
- **Lịch sử các môn đã thi:**
    - Cho phép người dùng xem lại các môn đã thi, cũng như các đáp án đã chọn đúng hoặc sai.

#### II. Một số kỹ thuật sử dụng
- Android UI/UX Libraries:
    - Sử dụng bộ thư viện từ trang: https://github.com/wasabeef/awesome-android-ui?fbclid=IwAR3pgu1yvdcgd3BCyfZTDMy2JkBVixGCc5o7OiEZ3oTCKLs9a_qTmGeuU6E
- Mô hình MVVM:
    - navigation điều hướng các fragment(viewModel).
    - Koin để tiêm các class(DI).
    - Room database.
    - usercase để chia nhỏ dữ liệu từ repository.
    - flow coroutine + livedata .
    - databinding.
- FireStore:
    - Sử dụng FireStore để lưu các bộ đề, khi người dùng online sẽ hiển thị các đề thi, người dùng có thể thi onl hoặc tải đề thi về database sqlLite để thi off.
- Các thành phần sử dụng:
    - RecycleView: sử dụng listAdapter để tối ưu.
    - ViewPage2+TabLayout để thực hiện vuốt các item, hoặc lựa chọn các tab phía dưới.
    - Xử lí đa màn hình.

#### III. Ảnh Demo
![2D](https://play-lh.googleusercontent.com/VHn6teF41mG1XzN_44UZgA5XRCURRi3_MizaHhJIBU_SPz2kKAEqhkgrYMng7I9-TrY-=w1366-h657-rw)

![3D](https://play-lh.googleusercontent.com/EFFi2tB1gdtRo503KsFHMVtRd4x-L63LGrFmiqf-WuyKlxa1Dk9a6I14W_QnKsaYuA=w720-h310-rw)

![4D](https://play-lh.googleusercontent.com/O5iFVPSq6az0oujSFbU9gwNtBo5k3kuKm5nHepSY6J2CfWx_O3sms5F4AW2Ml7HEe-Y=w720-h310-rw)

![Impossible Triangle](https://play-lh.googleusercontent.com/1M4WQs0Dm-T2OolWRKSz1cn11Q_zP8o5m6MVeGfE6xA0OIaL5EtxFIC8Y2rqxz6zuA=w720-h310-rw)

#### III. Link tải app
https://play.google.com/store/apps/details?id=com.baokiin.uisptit&hl=vi&gl=US




 






