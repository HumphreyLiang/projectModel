package com.member.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.model.Member;
import com.member.model.MemberService;

@WebServlet("/front_end/member/Update")
@MultipartConfig
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		MemberService memSvc = new MemberService();
		
		if ("memUpdate".equals(action)) {
		
			List<String> errorMsgs = new LinkedList<String>();
			
			try {
				/***************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String memSname = req.getParameter("memSname");
				if (memSname == null || memSname.trim().isEmpty()) {
					errorMsgs.add("�ж�g�ʺ�");
				}

				String memName = req.getParameter("memName");
				if (memName == null || memName.trim().isEmpty()) {
					errorMsgs.add("�ж�g�m�W");
				}

				java.sql.Date memBday = null;
				try {
					memBday = java.sql.Date.valueOf(req.getParameter("memBday"));
				} catch (IllegalArgumentException e) {
					errorMsgs.add("����榡���~");
				}
				
				String memPhone = req.getParameter("memPhone");
				if (!(memPhone.matches("[09]{2}[0-9]{2}-[0-9]{6}") || memPhone.matches("[09]{2}[0-9]{8}"))) {
					errorMsgs.add("����榡���~");
				}

				Integer memGender = null;
				try {
					memGender = Integer.parseInt(req.getParameter("memGender").trim());
				} catch (IllegalArgumentException e) {
					memGender = 2;
					errorMsgs.add("�п�J�ʧO");
				}

				Integer memRelation = null;
				try {
					memRelation = Integer.parseInt(req.getParameter("memRelation").trim());
				} catch (IllegalArgumentException e) {
					memRelation = 2;
					errorMsgs.add("�п�J�P�����A");
				}

				String memEmail = req.getParameter("memEmail");
				if (!memEmail.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")) {
					errorMsgs.add("�п�J���T��Email�H�c");
				}

				String memAddress = req.getParameter("memAddress");
				if (memAddress == null || memAddress.trim().isEmpty()) {
					errorMsgs.add("�п�J���T���a�}");
				}

				String memSelfintro = req.getParameter("memSelfintro");
				if (memSelfintro == null || memSelfintro.trim().isEmpty()) {
					errorMsgs.add("�п�J���T���a�}");
				}

				Member memberU = new Member();
				memberU.setMemSname(memSname);
				memberU.setMemName(memName);
				memberU.setMemBday(memBday);
				memberU.setMemPhone(memPhone);
				memberU.setMemGender(memGender);
				memberU.setMemRelation(memRelation);
				memberU.setMemEmail(memEmail);
				memberU.setMemAddress(memAddress);
				memberU.setMemSelfintro(memSelfintro);
				byte[] memImg = member.getMemImg();
				Collection<Part> parts = req.getParts();

				for (Part part : parts) {
					if (part.getName().equals("memImg") && getFileNameFromPart(part) != null
							&& part.getContentType().startsWith("image")) {
						memImg = getPictureByteArray(part.getInputStream());
						memberU.setMemImg(memImg);
					}
					if (getFileNameFromPart(part) != null && part.getName().equals("memImg")
							&& !(part.getContentType().startsWith("image"))) {
						errorMsgs.add("�Ӥ��榡���~");
					}
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher dispatcher = req.getRequestDispatcher("/memberInfoUpdate.jsp");
					req.setAttribute("errorMsgs", errorMsgs);
					req.setAttribute("member", memberU);
					dispatcher.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				
				memSvc.updateMember(member.getMemNo(), member.getMemId(), member.getMemPwd(), memName, memSname,
						memGender, member.getMemIdNo(), memBday, memPhone, memAddress, memEmail, memImg,
						member.getMemReported(), member.getMemStatus(), memRelation, memSelfintro,
						member.getMemFollowed(), member.getMemPoint(), member.getMemSaleRank(),
						member.getMemLongtitude(), member.getMemLatitude(), member.getMemLocTime(),
						member.getMemLocStatus());
				/***************************
				 * 3.�ק粒��,�ǳ����(Send the Success view)
				 *************/
				Integer memNo = member.getMemNo();
				session.removeAttribute("member");
				Member newMember = memSvc.getOneMember(memNo);
				session.setAttribute("member", newMember);
				res.sendRedirect(req.getContextPath() + "/memberInfo.jsp");
			} catch (Exception e) {
				System.out.println("error");
			}
		}

		//�ק�K�X
		if ("pwdChange".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			
			/***************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			
			String memPwd=req.getParameter("memPwd");
			if(!memPwd.equals(member.getMemPwd())){
				errorMsgs.add("�ثe���K�X���~");
			}
			
			String memNewPwd=req.getParameter("memNewPwd");
			if(!(memNewPwd.matches(".*[a-zA-Z]+.*")&&memNewPwd.trim().length()>5)){
				errorMsgs.add("�s�K�X�榡����");
			}
			

			Map<String,String> falsePwd=new HashMap<String,String>();
			falsePwd.put("memPwd", memPwd);
			falsePwd.put("memNewPwd", memNewPwd);
			
			if(!errorMsgs.isEmpty()){
				RequestDispatcher dispatcher=req.getRequestDispatcher("memPwdChange.jsp");
				req.setAttribute("falsePwd", falsePwd);
				req.setAttribute("errorMsgs", errorMsgs);
				dispatcher.forward(req, res);
				return;
			}
			
			
			/*************************** 2.�}�l�ק��� *****************************************/
			memSvc.updateMember(member.getMemNo(), member.getMemId(), memNewPwd, member.getMemName(), member.getMemSname(), member.getMemGender(), member.getMemIdNo(),
					member.getMemBday(), member.getMemPhone(), member.getMemAddress(), member.getMemEmail(), member.getMemImg(), member.getMemReported(), member.getMemStatus(),
					member.getMemRelation(), member.getMemSelfintro(), member.getMemFollowed(), member.getMemPoint(), member.getMemSaleRank(),
					member.getMemLongtitude(), member.getMemLatitude(), member.getMemLocTime(), member.getMemLocStatus());
			
			/**************************** 3.�ק粒��,�ǳ����(Send the Success view)*************/
			Integer memNo = member.getMemNo();
			session.removeAttribute("member");
			Member newMember = memSvc.getOneMember(memNo);
			session.setAttribute("member", newMember);
			RequestDispatcher dispatcher=req.getRequestDispatcher("memPwdChange.jsp");
			req.setAttribute("success", "�K�X�ק令�\");
			dispatcher.forward(req, res);

			
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}

	public static byte[] getPictureByteArray(InputStream fis) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}