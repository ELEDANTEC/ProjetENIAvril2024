package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LoginController(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletResponse response) throws IOException {
        LOGGER.info("Tentative de connexion pour l'utilisateur {}", username);

        String sql = "SELECT * FROM USERS WHERE username = ? AND password = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });

        if (user == null) {
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
            LOGGER.info("Connexion échouée pour l'utilisateur {}", username);
            return "login";
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        LOGGER.info("Connexion réussie pour l'utilisateur {}", username);
        return "redirect:/Register.html";
    }

}
