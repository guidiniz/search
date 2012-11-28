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
import org.apache.hadoop.fs.shell.find.Or;
import org.apache.hadoop.fs.shell.find.Result;
import org.junit.Test;

public class TestOr extends TestExpression {

  @Test
  public void testPass()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.PASS);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.PASS);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.PASS, or.apply(pathData));
    verify(first).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }

  @Test
  public void testFailFirst()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.FAIL);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.PASS);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.PASS, or.apply(pathData));
    verify(first).apply(pathData);
    verify(second).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }

  @Test
  public void testFailSecond()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.PASS);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.FAIL);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.PASS, or.apply(pathData));
    verify(first).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }
  @Test
  public void testFailBoth()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.FAIL);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.FAIL);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.FAIL, or.apply(pathData));
    verify(first).apply(pathData);
    verify(second).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }

  @Test
  public void testStopFirst()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.STOP);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.PASS);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.STOP, or.apply(pathData));
    verify(first).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }

  @Test
  public void testStopSecond()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.PASS);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.STOP);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.PASS, or.apply(pathData));
    verify(first).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }

  @Test
  public void testStopFail()  throws IOException {
    Or or = new Or();
    
    PathData pathData = mock(PathData.class);
    
    Expression first = mock(Expression.class);
    when(first.apply(pathData)).thenReturn(Result.FAIL);
    
    Expression second = mock(Expression.class);
    when(second.apply(pathData)).thenReturn(Result.STOP);
    
    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    assertEquals(Result.STOP, or.apply(pathData));
    verify(first).apply(pathData);
    verify(second).apply(pathData);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }
  @Test
  public void testInit()  throws IOException {
    Or or = new Or();
    Expression first = mock(Expression.class);
    Expression second = mock(Expression.class);

    Deque<Expression> children = new LinkedList<Expression>();
    children.add(second);
    children.add(first);
    or.addChildren(children);
    
    FindOptions options = mock(FindOptions.class);
    or.initialise(options);
    verify(first).initialise(options);
    verify(second).initialise(options);
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }
}
