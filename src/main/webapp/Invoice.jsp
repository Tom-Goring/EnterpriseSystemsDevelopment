<%-- 
    Document   : invoice
    Created on : 18-Jan-2021, 18:39:17
    Author     : Bredan
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>SmartCare Surgery Invoice</title>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=PT+Sans&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/invoice.css"/>
    </head>
    <body>
        <div class="invoice-box">
            <table cellpadding="0" cellspacing="0">
            <tr class="top">
                <td colspan="2">
                    <table>
                        <tr>
                            <td class="title">
                                <img src="${pageContext.request.contextPath}/images/temp_logo.png" style="width:100%; max-width:300px;">
                            </td>
                            
                            <td class="date">
                                Created: ${requestScope.invoice.getFormattedIssueDate()}<br>
                                Due: ${requestScope.invoice.getFormattedDueDate()}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            
            <tr class="information">
                <td colspan="2">
                    <table>
                        <tr>
                            <td>
                                SmartCare Surgery<br>
                                2 Smart Road<br>
                                LD1 2ND
                            </td>
                            
                            <td>
                                ${requestScope.user.getFirstName()}, ${requestScope.user.getSurname()}<br>
                                ${requestScope.user.getEmail()}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            
            <tr class="heading">
                <td>
                    Service
                </td>
                
                <td>
                    Price
                </td>
            </tr>
            
            <tr class="item">
                <td>
                    ${requestScope.invoice.getService()}
                </td>
                
                <td>
                    £${requestScope.invoice.getAmount()}
                </td>
            </tr>
            
            <tr class="total">
                <td></td>
                
                <td>
                   Total: £${requestScope.invoice.getAmount()}
                </td>
            </tr>
            </table>
        </div>
    </body>
</html>