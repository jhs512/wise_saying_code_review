package wisesaying.util;

import java.util.Map;

public record WiseSayingCondition(Long pageNum, String wiseSaying, String writer) {
	public WiseSayingCondition() {
		this(null, null, null);
	}

	public static WiseSayingCondition createCondition(Map<String, String> params) {
		Long pageNum = params.containsKey("page") ?
			Long.parseLong(params.get("page")) : null;
		String writer = params.containsKey("writer") ? params.get("writer") : null;
		String wiseSaying = params.containsKey("wisesaying") ? params.get("wisesaying") : null;

		return new WiseSayingCondition(pageNum, wiseSaying, writer);
	}
}
