import com.alibaba.fastjson.JSON;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.jputils.dto.InvoiceInfoDto;
import com.jpmsoft.jputils.feign.SysConfigFeign;
import com.jpmsoft.jputils.response.InvoiceResponse;
import com.jpmsoft.jputils.utils.InvoiceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author yb.Sun
 * @date 2025/1/17 09:29
 */
public class InvoiceDemoTest {

    @Test
    public void test() {
        // String appCode = "5fe7b1220f5c44459605c2271e7d80b8";
        // MultipartFile file = null;
        // try {
        //     file = InvoiceUtils.getMultipartFile("C:\\Users\\Dell\\Desktop\\90645651-7995-490a-b696-068bb624ac63.pdf");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // InvoiceInfoDto invoiceInfoDto =InvoiceUtils.getInvoiceInfo(file, appCode, "normal");
        // System.out.println("invoiceInfoDto"+JacksonUtils.toJSONString(invoiceInfoDto));
    }
}
