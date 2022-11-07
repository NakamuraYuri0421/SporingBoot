package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/** セキュリティの対象外を設定 */
	@Override
	public void configure(WebSecurity web) throws Exception {

		// セキュリティを適用しない
		web.ignoring().antMatchers("/webjars/**").antMatchers("/css/**").antMatchers("/js/**")
				.antMatchers("/h2-console/**");
	}

	/** セキュリティの各種設定 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// ログイン不要ページの設定
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/user/signup").permitAll().anyRequest()
				.authenticated(); // それ以外は直リンクNG

		// ログイン処理
		http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/login?error")
				.usernameParameter("userId").passwordParameter("password").defaultSuccessUrl("/user/list", true);
		// CSRF対策を無効に設定（一時的）
		http.csrf().disable();
	}

	/**認証の設定*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		
		//インメモリ認証
		auth.inMemoryAuthentication().withUser("user").password("user").roles("GENERAL").and().withUser("admin").password("admin").roles("ADMIN");
	}
}
