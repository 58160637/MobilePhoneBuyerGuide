import com.scb.mobilephonebuyerguid.model.Mobile
import retrofit2.Call
import retrofit2.http.GET

interface MobileApiService {

    @GET("api/mobiles/")
    fun mobiles(): Call<List<Mobile>>
}
