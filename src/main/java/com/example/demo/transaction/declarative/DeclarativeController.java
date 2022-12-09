package com.example.demo.transaction.declarative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CustomerEntity;


/**
 * コントローラサンプル＜宣言的トランザクション＞
 * @Transactionalでトランザクション制御
 * 
 * @author yatty0812
 */
@RestController
public class DeclarativeController {
	
	/*****************************************************
	 【３】宣言的トランザクション-サンプルコード
	******************************************************/
	@Autowired
	DeclarativeService decService;

	/**
	 * コントローラメソッド
	 * 【ポイント】
	 *   @Transactional付けてます（メソッド単位での付与としてます）
	 */
	@RequestMapping("/dec")
	@Transactional
	public void test() {
		
		// ============================================
		// サービスメソッド１呼び出し
		// ⇒@Transacitonal付きメソッド（トランザクション伝搬）
		// 
		// 本メソッド最後のExceptionスローでロールバックされる
		// ============================================
		CustomerEntity CustomerEntity1 = new CustomerEntity();
		CustomerEntity1.id = 13;
		CustomerEntity1.name ="猫島";
		int insertCount = decService.insert(CustomerEntity1);
		
		
		// ============================================
		// サービスメソッド２呼び出し
		// ⇒@Transacitonal(propagation = Propagation.REQUIRES_NEW)付きメソッド（別トランザクション）
		// 
		// こちらはトランザクションコミットされる
		// ============================================
		CustomerEntity CustomerEntity2 = new CustomerEntity();
		CustomerEntity2.id = 2;
		CustomerEntity2.name ="柴田";
		int updateCount = decService.update(CustomerEntity2);
		
		// ロールバック確認用にExceptionスロー
		throw new RuntimeException("/dec Exception");
	}
}
