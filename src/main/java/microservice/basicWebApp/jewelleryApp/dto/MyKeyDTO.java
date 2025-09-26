package microservice.basicWebApp.jewelleryApp.dto;

import com.fasterxml.jackson.annotation.JsonKey;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class MyKeyDTO {
    String prefix;
    String key;
    String suffix;
    @JsonKey
    String getMyKey(){
        return "==="+this.prefix+this.key+this.suffix+"===";
    }
}
