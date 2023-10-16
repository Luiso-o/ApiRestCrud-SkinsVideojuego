package Skin.VideoGame.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
/**
 * Luis
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Luis",
                        email = "cheportillo@gmail.com",
                        url = "https://github.com/Luiso-o"
                ),
                description = "API para gestionar jugadores y los diferentes skins que adquieren a lo largo del juego",
                title = "Juego de aventura y fantasia",
                version = "1.0"
                ),
        servers = {
                @Server(
                        description = "local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class ApiConfig {
}
