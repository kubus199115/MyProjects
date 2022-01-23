/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.dto.Comment;
import com.mycompany.entity.comment;
import java.util.List;

/**
 *
 * @author Kubus
 */
public interface CommentRepository {
    void addComment(Comment comment, String woNumber, String type, String username);
    List<comment> findCommentByWorkOrder(String woNumber);
}
