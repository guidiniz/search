package org.apache.hadoop.fs.shell.find;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.hadoop.fs.shell.PathData;
import org.apache.hadoop.fs.shell.find.Expression;
import org.apache.hadoop.fs.shell.find.FindOptions;
import org.apache.hadoop.fs.shell.find.Not;
import org.apache.hadoop.fs.shell.find.Result;
import org.junit.Test;

public class TestNot extends TestExpression {

  @Test
  public void applyPass()  throws IOException {
    Not not = new Not();
    
    PathData pathData = mock(PathData.class);
    
    Expression child = mock(Expression.class);
    when(child.apply(pathData)).thenReturn(Result.PASS);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(child);
    not.addChildren(children);
    
    assertEquals(Result.FAIL, not.apply(pathData));
    verify(child).apply(pathData);
    verifyNoMoreInteractions(child);
  }

  @Test
  public void applyFail()  throws IOException {
    Not not = new Not();
    
    PathData pathData = mock(PathData.class);
    
    Expression child = mock(Expression.class);
    when(child.apply(pathData)).thenReturn(Result.FAIL);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(child);
    not.addChildren(children);
    
    assertEquals(Result.PASS, not.apply(pathData));
    verify(child).apply(pathData);
    verifyNoMoreInteractions(child);
  }
  @Test
  public void testInit()  throws IOException {
    Not not = new Not();
    Expression child = mock(Expression.class);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(child);
    not.addChildren(children);
    
    FindOptions options = mock(FindOptions.class);
    not.initialise(options);
    verify(child).initialise(options);
    verifyNoMoreInteractions(child);
  }
}
