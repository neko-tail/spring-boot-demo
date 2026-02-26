一个简单的 spring boot 单体应用 demo，尝试打包成 native image 使用

## required

- GraalVM 25

## build

修改 `./src/main/resources/application.yml` 中的数据库连接配置

jar

```sh
./mvnw clean package
```

native image（较慢，需等待几分钟）

```sh
./mvnw -Pnative clean native:compile
```

## run

jar

```sh
java -jar ./target/demo.jar
```

native image

```sh
./target/demo
```

## use

启动后见 swagger：http://localhost:8080/swagger-ui

## 参考

[docs/spring-boot.md](docs/spring-boot.md)
