package dev.yubin.imageconverter.api.convert.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsciiCompleteRequest {

	@NotBlank(message = "requestId는 필수입니다.")
	private String requestId;

	@NotBlank(message = "userId는 필수입니다.")
	private String userId;

	@NotBlank(message = "txtUrl은 필수입니다.")
	private String txtUrl;
}
