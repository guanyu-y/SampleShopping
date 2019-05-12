package la.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.mail.*;

import la.bean.CartBean;
import la.bean.CustomerBean;
import la.dao.DAOException;
import la.dao.OrderDAO;

import la.mail.MailTransfer;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 注文処理の業務は全てセッションとCartが存在することが前提
		HttpSession session = request.getSession(false);
		if(session==null) { // セッションオブジェクトがない場合
			request.setAttribute("message","セッションが切れています。もう一度トップページより操作してください。");
			gotoPage(request,response,"/errInternal.jsp");
			return;
		}
		CartBean cart = (CartBean) session.getAttribute("cart");
		if(cart==null) { // カートがない
			request.setAttribute("message", "正しく操作してください。");
			gotoPage(request,response,"/errInternal.jsp");
			return;
		}

		try{
			// パラメータ解析
			String action = request.getParameter("action");
			// input_customerまたはパラメータなしの場合は顧客情報入力ページを表示
			if(action==null || action.length()==0 || action.equals("input_customer")) {
				gotoPage(request,response,"/customerInfo.jsp");
			}
			// confirmは確認処理を行う
			else if(action.equals("confirm")) {
				String name = request.getParameter("name");
				String address = request.getParameter("address");
				String tel = request.getParameter("tel");
				String email = request.getParameter("email");
				
				// 顧客情報のチェック
				if(name==null || name.length()==0 ||
						address==null || address.length()==0 ||
							tel==null || tel.length()<=9 ||  tel.length()>=12 ||
								email==null || email.length()==0) {
					request.setAttribute("reEnter", "入力内容に不備があります。もう一度入力してください。");
				}
				try {
					long integer = 0;
					integer = Long.parseLong(tel);
				}catch(NumberFormatException e) {
					//System.out.println("NumberFormatExeption例外をキャッチしました");
					request.setAttribute("telError", "電話番号は半角数字で入力してください。");
				}
				
				if(request.getAttribute("reEnter")!=null || request.getAttribute("telError")!=null) {
					gotoPage(request,response,"/customerInfo.jsp");
					return;
				}
				
				
				CustomerBean bean = new CustomerBean();
				bean.setName(name);
				bean.setAddress(address);
				bean.setTel(tel);
				bean.setEmail(email);
				
				
				session.setAttribute("customer", bean);
				gotoPage(request,response,"/confirm.jsp");

			}
			// orderは注文確定
			else if(action.equals("order")) {
				CustomerBean customer = (CustomerBean) session.getAttribute("customer");
				if(customer==null) { // 顧客情報がないなら
					request.setAttribute("message",  "正しく操作してください。");
					gotoPage(request,response,"/errInternal.jsp");
				}
							
				OrderDAO order = new OrderDAO();
				int orderNumber = order.saveOrder(customer, cart);
	
				// クライアントへメールを送信(java.util.JavaMail)
				MailTransfer objSend = new MailTransfer();
				boolean isSend = objSend.sendMail(orderNumber, customer, cart);
				request.setAttribute("isSend",isSend);

				// 注文後はセッション情報をクリア
				session.removeAttribute("cart");
				session.removeAttribute("customer");
				// 注文番号をクライアントへ送信
				request.setAttribute("orderNumber", new Integer(orderNumber));
				gotoPage(request,response,"/order.jsp");



			}else { // actionの値が不正
				request.setAttribute("message",  "正しく操作してください。");
				gotoPage(request,response,"/errInternal.jsp");
			}

		}catch(DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request,response,"/errInternal.jsp");
		}
	}


	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
