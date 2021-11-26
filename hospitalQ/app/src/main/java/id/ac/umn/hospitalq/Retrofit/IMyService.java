package id.ac.umn.hospitalq.Retrofit;

import java.util.ArrayList;

import id.ac.umn.hospitalq.AntrianData;
import id.ac.umn.hospitalq.BookingData;
import id.ac.umn.hospitalq.DepartementData;
import id.ac.umn.hospitalq.DoctorData;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyService {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("firstname") String firstname,
                                    @Field("lastname") String lastname,
                                    @Field("hari") int hari,
                                    @Field("bulan") int bulan,
                                    @Field("tahun") int tahun,
                                    @Field("alamat") String alamat,
                                    @Field("departement") String departement,
                                    @Field("userrole") int userrole);
    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                    @Field("password") String password);
    @POST("getuser")
    @FormUrlEncoded
    Observable<String> getUser(@Field("email") String email,
                                 @Field("password") String password);
    @POST("updateuser")
    @FormUrlEncoded
    Observable<String> updateUser(@Field("email") String email,
                                    @Field("firstname") String firstname,
                                    @Field("lastname") String lastname,
                                    @Field("hari") int hari,
                                    @Field("bulan") int bulan,
                                    @Field("tahun") int tahun,
                                    @Field("alamat") String alamat,
                                    @Field("departement") String departement);

    @POST("getalldoctor")
    @FormUrlEncoded
    Observable<String> getAlldoctor(@Field("email") String email,
                                  @Field("password") String password);
    @POST("getalldoctor")
    @FormUrlEncoded
    Call<ArrayList<DoctorData>> getAllDoctors(@Field("email") String email,
                                              @Field("password") String password);

    @POST("getdoctorbydepartement")
    @FormUrlEncoded
    Observable<String> getDoctorbydepartement(@Field("email") String email,
                                    @Field("password") String password,
                                              @Field("departement") String departement);
    @POST("getcurrentdoctorantrian")
    @FormUrlEncoded
    Observable<String> getCurrentDoctorantrian(@Field("email") String email,
                                        @Field("password") String password);
    @POST("getdoctorantrian")
    @FormUrlEncoded
    Observable<String> getDoctorantrian(@Field("email") String email,
                               @Field("password") String password);
    @POST("getuserantrian")
    @FormUrlEncoded
    Call<ArrayList<BookingData>> getBooking(@Field("email") String email,
                                               @Field("password") String password);
    @POST("getallantrian")
    @FormUrlEncoded
    Call<ArrayList<AntrianData>> getAllAntrian(@Field("email") String email,
                                               @Field("password") String password);
    @POST("zeroantrian")
    @FormUrlEncoded
    Observable<String> zeroAntrian(@Field("email") String email,
                                      @Field("password") String password);
    @POST("incrementantrian")
    @FormUrlEncoded
    Observable<String> incrementAntrian(@Field("email") String email,
                                   @Field("password") String password);
    @POST("getdepartement")
    @FormUrlEncoded
    Call<ArrayList<DepartementData>> getDepartement(@Field("email") String email,
                                                    @Field("password") String password);
    @POST("addantrian")
    @FormUrlEncoded
    Observable<String> addAntrian(@Field("user_name") String user_name,
                                      @Field("doktor_name") String doktor_name,
                                  @Field("doktor_email") String doktor_email);
    @POST("adddepartement")
    @FormUrlEncoded
    Observable<String> addDepartement(@Field("departement") String departement);
}
