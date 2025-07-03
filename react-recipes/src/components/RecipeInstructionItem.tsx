import React, { forwardRef } from 'react';
import styled from 'styled-components';
import type { Instruction } from '../data/models';

interface RecipeInstructionItemProps {
  instruction: Instruction;
  index: number;
  isCurrent?: boolean;
  onClick?: () => void;
  ref?: React.Ref<HTMLDivElement>;
}

const InstructionContainer = styled.div<{ isCurrent?: boolean }>`
  display: flex;
  padding: 16px 24px;
  margin-bottom: 8px;
  background-color: ${({ theme, isCurrent }) => 
    isCurrent ? theme.colors.backgroundSelected : 'transparent'};
  border-radius: 8px;
  cursor: ${({ onClick }) => (onClick ? 'pointer' : 'default')};
  transition: background-color 0.2s ease;

  &:hover {
    background-color: ${({ theme, isCurrent }) => 
      isCurrent ? theme.colors.backgroundSelected : theme.colors.backgroundSurface1Hover};
  }
`;

const NumberContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.backgroundBrand};
  color: ${({ theme }) => theme.colors.onBackgroundBrand};
  font-weight: 600;
  margin-right: 16px;
  flex-shrink: 0;
`;

const ContentContainer = styled.div`
  flex: 1;
`;

const InstructionText = styled.p`
  color: ${({ theme }) => theme.colors.foregroundDefault};
  line-height: 1.6;
`;

const SectionTitle = styled.h4`
  margin-bottom: 8px;
  color: ${({ theme }) => theme.colors.foregroundBrand};
  font-size: 1.1rem;
`;

const RecipeInstructionItem = forwardRef<HTMLDivElement, RecipeInstructionItemProps>(
  ({ instruction, index, isCurrent = false, onClick }, ref) => {
    return (
      <InstructionContainer ref={ref} isCurrent={isCurrent} onClick={onClick}>
        <NumberContainer>{index + 1}</NumberContainer>
        <ContentContainer>
          {instruction.section && <SectionTitle>{instruction.section}</SectionTitle>}
          <InstructionText>{instruction.text}</InstructionText>
        </ContentContainer>
      </InstructionContainer>
    );
  }
);

export default RecipeInstructionItem;
