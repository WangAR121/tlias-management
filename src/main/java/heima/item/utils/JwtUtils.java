package heima.item.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JWT 令牌工具类（生成令牌 / 解析令牌）。
 *
 * <p>签名算法：HS256（HMAC-SHA256），密钥取自下面的 SECRET 常量，
 * 以 UTF-8 字节直接作为 HMAC 密钥。SECRET 长度 40 个字符、UTF-8 共 110 字节（880 位），
 * 满足 HS256 对密钥至少 256 位的要求。</p>
 *
 * <p>令牌结构：header.payload.signature，其中 payload 里除了调用方传入的自定义数据，
 * 还自动带上 iat（签发时间）和 exp（过期时间）。解析时会自动校验签名和过期时间，
 * 签名被篡改或已过期都会抛出 {@link io.jsonwebtoken.JwtException} 的子类。</p>
 *
 * <p>典型用法（登录成功后签发，拦截器里解析）：
 * <pre>{@code
 * String token = JwtUtils.generateToken(Map.of("id", emp.getId(), "username", emp.getUsername()));
 * Claims claims = JwtUtils.parseToken(token);
 * Integer id = claims.get("id", Integer.class);
 * }</pre></p>
 */
public class JwtUtils {

    /** 密钥（本项目专用）：正式项目应放到配置文件中，不要硬编码在代码里 */
    private static final String SECRET = "IuesrOS4gOS4qumhueebrnRsaWFz566h55CG57O757uf77yM5oiR6KaB5Yqq5Yqb5a6M5oiQ5aSa5Liq6aG555uu5om+5Yiw5a6e5Lmg5Yqg5rK55Yqg5rK55Yqg5rK55Yqg5rK55Yqg5rK5562JIg==";

    /** HMAC 密钥对象：由 SECRET 的 UTF-8 字节生成，HS256 要求密钥长度不少于 32 字节 */
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /** 令牌有效期：2 小时 */
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000L;

    /** 工具类不允许被实例化 */
    private JwtUtils() {
    }

    /**
     * 生成 JWT 令牌。
     *
     * @param claims 自定义负载数据，例如 {@code Map.of("id", 1, "username", "Tom")}；可为 null
     * @return 签名后的令牌字符串（header.payload.signature）
     */
    public static String generateToken(Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        // 容错：claims 传 null 时用一个空 Map，避免底层 putAll(null) 抛 NPE
        Map<String, Object> payload = (claims == null) ? new LinkedHashMap<>() : claims;
        return Jwts.builder()
                .claims(payload)                         // 自定义负载
                .issuedAt(new Date(now))                 // 签发时间 iat
                .expiration(new Date(now + EXPIRE_TIME)) // 过期时间 exp
                .signWith(KEY, Jwts.SIG.HS256)           // HS256 签名
                .compact();
    }

    /**
     * 解析 JWT 令牌。
     *
     * <p>会校验签名与过期时间：签名不合法抛 {@code SignatureException}，
     * 令牌已过期抛 {@code ExpiredJwtException}，格式错误抛 {@code MalformedJwtException}，
     * 它们都是 {@link io.jsonwebtoken.JwtException} 的子类。</p>
     *
     * @param token 令牌字符串（不含 "Bearer " 前缀）
     * @return 令牌负载 Claims，可用 {@code claims.get("id", Integer.class)} 取自定义数据
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)          // 用同一把密钥验签
                .build()
                .parseSignedClaims(token) // 解析并校验
                .getPayload();
    }
}