package la.mail;
import java.util.Properties;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import la.bean.CustomerBean;
import la.bean.CartBean;
import la.bean.ItemBean;

public class MailTransfer {
	
		
	
		public boolean sendMail(int orderNumber, CustomerBean customer, CartBean cart){
		Properties objPro = new Properties();
		
        //GmailのSMTPを使う場合
        objPro.put("mail.smtp.auth", "true");
        objPro.put("mail.smtp.starttls.enable", "true");
        objPro.put("mail.smtp.host", "smtp.gmail.com");
        objPro.put("mail.smtp.port", "587");
        objPro.put("mail.smtp.debug", "true");
		
		boolean ret = false;
		
		// メールセッションの確保
		Session session = Session.getDefaultInstance(objPro, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("yoyamada0789@gmail.com", "2019qrigood");
                // User and Password not acceptedエラー 
                // -> Gmail側のセキュリティ設定を変更する(安全性の低いアプリのアクセス有効化)
            }
        });
		
		// 送信メッセージオブジェクトを生成
		MimeMessage objMsg = new MimeMessage(session);
		
		// InternetAddressの作成
		InternetAddress[] toAddress = new InternetAddress[1];
		try{
			toAddress[0] = new InternetAddress(customer.getEmail());
		}catch (AddressException e) {
			e.printStackTrace();
			System.out.println("AddressExceptionにキャッチされました");
		}
		
		
		final String charset = "ISO-2022-JP";
		final String encoding = "text/html";
		
		// カートから商品を取り出す
		HashMap<Integer,ItemBean> items = (HashMap<Integer,ItemBean>)cart.getItems();
		
		Set<Integer> keys = items.keySet();
		String itemList = "";
		for(Integer key : keys)
			itemList += items.get(key).getName() + " [" + items.get(key).getQuantity() + "点]" + "：小計 " + items.get(key).getSubTotalCurrency() + "<br>";
		
		String total = cart.getTotalCurrency();
		
		try{
			// 送信先(TO)
			objMsg.setRecipients(MimeMessage.RecipientType.TO, toAddress);
			// 送信元（Fromヘッダ)
			InternetAddress fromAddress = new InternetAddress("yoyamada0789@gmail.com");
			objMsg.setFrom(fromAddress);
			
			// 件名 -------------------------------------------------
			String subject = "【サンプルショッピング】ご注文確認メール";
			objMsg.setSubject(subject, charset);
			
			// 本文 =================================================
			String text = 
					customer.getName() + "さま <br><br>"
					+ "この度はサンプルショッピングサイトをご利用頂きまして、誠に有難うございます。<br><br>"
					+ "お客様のご注文が確定しました。<br>"
					+ "このメールはご利用明細に代わるものでございますので、大切に保管くださいますようお願い致します。<br>"
					+ "------------<br>"
					+ "[ご注文内容] <br>"
					+ "お客様名：" + customer.getName() + "様 <br>"
					+ "ご注文番号：" + orderNumber + "<br>"
					+ "ご注文商品：<br>" + itemList
					+ "総計：" + total + "<br>"
					+ "-----------------------------------<br>"
					+ "[お届け先住所]<br>"
					+ customer.getAddress() + "<br>"
					+ "-----------------------------------<br>"
					+ "このメールは自動配信でお届けしております。<br>"
					+ "今後ともサンプルショッピングをご利用賜りますよう、宜しくお願い申し上げます。<br>"
					+ "------------<br>"
					+ "<a href=\"http://localhost:8090/shopping/ShowItemServlet?action=top\">http://sample.ne.jp/shopping/</a>"
					;
			objMsg.setText(text,charset);
			
			// 送信日
			objMsg.setSentDate(new Date());
			
			
			//-------------------------------------------------------
			
			// メールの形式(ヘッダー)
			objMsg.setHeader("Content-Type",encoding);
			
			
			// 設定の保存
			objMsg.saveChanges();
			
			// メール送信
			Transport.send(objMsg);			System.out.println("メールを送信しました");	ret = true;
			
		}catch(MessagingException e) {
			System.out.println("MessaginExceptionにキャッチされました");
			e.printStackTrace();
		}
		return ret;
	}
}
